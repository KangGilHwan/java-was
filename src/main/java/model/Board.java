package model;

public class Board {

    private Long id;
    private String userId;
    private String name;
    private String contents;

    public Board() {
    }

    public Board(Long id, String userId, String contents, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.contents = contents;
    }

    public String getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
