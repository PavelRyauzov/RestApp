package ru.ryauzov.restexample.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.format.annotation.DateTimeFormat;
import ru.ryauzov.restexample.entities.DocumentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "document")
public class DocumentDTO {
    @NotBlank(message = "Series is blank")
    @Size(min = 4, max = 4, message = "Series length should be 4")
    private String series;

    @NotBlank(message = "Number is blank")
    @Size(min = 6, max = 6, message = "Number length should be 6")
    private String number;

    @NotNull(message = "Document type is null")
    private DocumentType type;

    @NotNull(message = "Issue date is null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    @XmlAttribute(name = "series")
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @XmlAttribute(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @XmlAttribute(name = "type")
    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    @XmlAttribute(name = "issueDate")
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}
