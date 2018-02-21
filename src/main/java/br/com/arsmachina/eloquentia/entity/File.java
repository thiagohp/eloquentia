package br.com.arsmachina.eloquentia.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mongojack.ObjectId;

/**
 * Class that represents a file which was uploaded to Eloquentia.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private byte[] content;
    
    private Long modifiedDate;

    /**
     * Returns the internal database id.
     * 
     * @return a {@link String}.
     */
    @Id
    @ObjectId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Name of the file.
     * 
     * @return a {@link String}.
     */
    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "[a-zA-Z0-9\\.]+")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public long getModifiedDate() {
        if (modifiedDate == null) {
            setLastModifiedToNow();
        }
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * Updates the last modified time to now.
     */
    public void setLastModifiedToNow() {
        setModifiedDate(System.currentTimeMillis());
    }

}
