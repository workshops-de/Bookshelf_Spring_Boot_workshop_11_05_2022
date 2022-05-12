package de.workshops.bookshelf;

import java.util.Objects;

public class BookSearchParameter {
    private String authorName;
    private String title;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookSearchParameter that = (BookSearchParameter) o;
        return Objects.equals(authorName, that.authorName) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorName, title);
    }

    @Override
    public String toString() {
        return "BookSearchParameter{" +
                "authorName='" + authorName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
