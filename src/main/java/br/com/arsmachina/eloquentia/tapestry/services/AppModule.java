package br.com.arsmachina.eloquentia.tapestry.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.MarkupWriterListener;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.beanvalidator.BeanValidatorSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;
import org.apache.tapestry5.urlrewriter.URLRewriterRule;

import br.com.arsmachina.eloquentia.EloquentiaConstants;
import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.controller.UserController;
import br.com.arsmachina.eloquentia.controller.impl.PageControllerImpl;
import br.com.arsmachina.eloquentia.controller.impl.TagControllerImpl;
import br.com.arsmachina.eloquentia.controller.impl.UserControllerImpl;
import br.com.arsmachina.eloquentia.dao.PageDAO;
import br.com.arsmachina.eloquentia.dao.TagDAO;
import br.com.arsmachina.eloquentia.dao.UserDAO;
import br.com.arsmachina.eloquentia.dao.mongodb.PageDAOImpl;
import br.com.arsmachina.eloquentia.dao.mongodb.TagDAOImpl;
import br.com.arsmachina.eloquentia.dao.mongodb.UserDAOImpl;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.User;
import br.com.arsmachina.eloquentia.security.BcryptPasswordService;
import br.com.arsmachina.eloquentia.security.EloquentiaRealm;
import br.com.arsmachina.eloquentia.security.ObjectAction;
import br.com.arsmachina.eloquentia.security.ObjectPermissionChecker;
import br.com.arsmachina.eloquentia.security.PagePermissionChecker;
import br.com.arsmachina.eloquentia.security.PasswordHasher;
import br.com.arsmachina.eloquentia.tapestry.rss.TagChannelProvider;
import br.com.arsmachina.eloquentia.tapestry.urlrewriting.SubdomainPageLinkTransformer;
import br.com.arsmachina.eloquentia.tapestry.urlrewriting.SubdomainTagLinkTransformer;
import br.com.arsmachina.eloquentia.tapestry.urlrewriting.SubdomainURLRewriterRule;
import br.com.arsmachina.tapestry_rss.services.ChannelProvider;

/**
 * Eloquentia's main Tapestry-IoC module
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class AppModule {
	
	/**
	 * Declares some services.
	 */
	public static void bind(ServiceBinder binder) {
		binder.bind(UserDAO.class, UserDAOImpl.class);
		binder.bind(PageDAO.class, PageDAOImpl.class);
		binder.bind(TagDAO.class, TagDAOImpl.class);
		binder.bind(UserController.class, UserControllerImpl.class);
		binder.bind(PageController.class, PageControllerImpl.class);
		binder.bind(TagController.class, TagControllerImpl.class);
		binder.bind(EloquentiaRealm.class);
		binder.bind(PasswordService.class, BcryptPasswordService.class);
		binder.bind(PasswordHasher.class, BcryptPasswordService.class);
		binder.bind(UserValueEncoder.class);
		binder.bind(PageValueEncoder.class);
		binder.bind(PagePermissionChecker.class);
		binder.bind(UserService.class, UserServiceImpl.class);
		binder.bind(PageActivationContextService.class, PageActivationContextServiceImpl.class);
	}
	
	/**
	 * Buidls the {@link ObjectPermissionChecker} service.
	 * 
	 * @param contributions a {@link List} of {@link ObjectPermissionChecker}s.
	 * @param chainBuilder a {@link ChainBuilder}.
	 */
	@SuppressWarnings("rawtypes")
	@Marker(Primary.class)
	public static ObjectPermissionChecker buildObjectPermissionChecker(List<ObjectPermissionChecker> contributions, ChainBuilder chainBuilder) {
		
		final ObjectPermissionChecker terminator = new ObjectPermissionChecker() {
			@Override
			public Boolean isPermitted(User user, Object object, ObjectAction action) {
				return false;
			}
		};
		
		List<ObjectPermissionChecker> list = new ArrayList<ObjectPermissionChecker>(contributions);
		list.add(terminator);
		return chainBuilder.build(ObjectPermissionChecker.class, list);
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void contributeObjectPermissionChecker(OrderedConfiguration<ObjectPermissionChecker> configuration,
			PagePermissionChecker pagePermissionChecker) {
		configuration.add("page", pagePermissionChecker);
	}

	/**
	 * Apache Shiro/tapestry-security configuration. Basically, adds the {@link EloquentiaRealm}
	 * to it.
	 * 
	 * @param configuration a {@link Configuration} of {@link Realm}s.
	 * @param realm an {@link EloquentiaRealm}.
	 */
	public static void contributeWebSecurityManager(
			Configuration<Realm> configuration, EloquentiaRealm realm) {
		configuration.add(realm);
	}
	
	/**
	 * Defines some symbol values.
	 * @param configuration a {@link MappedConfiguration}.
	 */
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "8yhr349p8fy284723084uw-efudwoçierhf3f40v");
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,pt,pt_BR");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "0.0.1-SNAPSHOT");
	}

	/**
	 * Defines some symbol default values values.
	 * @param configuration a {@link MappedConfiguration}.
	 * @see EloquentiaConstants.
	 */
	public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(EloquentiaConstants.DATE_TIME_FIELD_FORMAT_SYMBOL, EloquentiaConstants.DEFAULT_DATE_TIME_FIELD_FORMAT_SYMBOL);
		configuration.add(EloquentiaConstants.PAGES_PER_PAGINATION_SYMBOL, EloquentiaConstants.DEFAULT_PAGES_PER_PAGINATION);
		configuration.add(EloquentiaConstants.TAG_CSS_CLASS_PREFIX_SYMBOL, EloquentiaConstants.DEFAULT_TAG_CSS_CLASS_PREFIX);
		configuration.add(EloquentiaConstants.GOOGLE_ANALYTICS_KEY_SYMBOL, "");
	}

	/**
	 * Contributes {@link ValueEncoder}s for {@link User} and {@link Page}.
	 * 
	 * @param configuration a {@link MappedConfiguration} of {@link Class} to {@link ValueEncoderFactory}.
	 * @param pageValueEncoder a {@link PageValueEncoder}.
	 * @param userValueEncoder an {@link UserValueEncoder}.
	 */
	public static void contributeValueEncoderSource(MappedConfiguration<Class<?>, ValueEncoderFactory<?>> configuration,
			PageValueEncoder pageValueEncoder, UserValueEncoder userValueEncoder) {
		configuration.add(User.class, userValueEncoder);
		configuration.add(Page.class, pageValueEncoder);
	}
	
	/**
	 * Contributes alternate {@link Translator}s.
	 * 
	 * @param configuration a {@link MappedConfiguration}.
	 * @see DateTimeTranslator
	 * @see TagsTranslator
	 */
	public static void contributeTranslatorAlternatesSource(
			MappedConfiguration<String, Translator<?>> configuration) { 
		configuration.addInstance("datetime", DateTimeTranslator.class);
		configuration.addInstance("tags", TagsTranslator.class);
	}
	
	/**
	 * Bean Validator configuration.
	 * 
	 * @param configuration an {@link OrderedConfiguration}.
	 */
	@Contribute(BeanValidatorSource.class)
	public static void provideBeanValidatorConfigurer(
			OrderedConfiguration<BeanValidatorConfigurer> configuration) {
		configuration.add("MyConfigurer", new BeanValidatorConfigurer() {
			public void configure(javax.validation.Configuration<?> configuration) {
				configuration.ignoreXmlConfiguration();
			}
		});
	}
	
	/**
	 * Contributes the URL rewriters.
	 * @param configuration an {@link OrderedConfiguration}.
	 */
	public static void contributeURLRewriter(OrderedConfiguration<URLRewriterRule> configuration) {
		configuration.addInstance("Subomain", SubdomainURLRewriterRule.class, "before:*");
	}
	
	/**
	 * Contributes the link transformers.
	 * @param configuration an {@link OrderedConfiguration}.
	 */
	@Contribute(PageRenderLinkTransformer.class)
	@Primary
	public static void addLinkTransformers(OrderedConfiguration<PageRenderLinkTransformer> configuration) {
		configuration.addInstance("SubdomainTag", SubdomainTagLinkTransformer.class);
		configuration.addInstance("SubdomainPage", SubdomainPageLinkTransformer.class, "before:SubdomainTag");
	}

	/**
	 * Contributes to the {@link ChannelProvider} service.
	 * @param configuration an {@link OrderedConfiguration}.
	 */
	@Contribute(ChannelProvider.class)
	public static void addChannelProviders(OrderedConfiguration<ChannelProvider> configuration) {
		configuration.addInstance("Tag", TagChannelProvider.class);
	}

	/**
	 * Prevents the stack from being added to all pages, which prevents tapestry-rss from working.
	 * 
	 * @param configuration an {@link OrderedConfiguration}.
	 */
	public static void contributeMarkupRenderer(OrderedConfiguration<MarkupWriterListener> configuration) {
		configuration.override("ImportCoreStack", null);
	}
	
}
