package com.azure.datasource.primary.database1;

import com.azure.spring.data.cosmos.core.mapping.Container;
import org.springframework.data.annotation.Id;

@Container(containerName = "books", ru = "400")
public class Book {
    @Id
    private String ibsn;

    private String name;

    private String author;

    public Book(String ibsn, String name, String author) {
        this.ibsn = ibsn;
        this.name = name;
        this.author = author;
    }

    public String getIbsn() {
        return ibsn;
    }

    public void setIbsn(String ibsn) {
        this.ibsn = ibsn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
            "ibsn='" + ibsn + '\'' +
            ", name='" + name + '\'' +
            ", author='" + author + '\'' +
            '}';
    }
}
