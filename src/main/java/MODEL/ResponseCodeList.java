package MODEL;

public class ResponseCodeList {
    private int code;
    private String description;
    private String imageUrl;
    private String dogImageUrl; 
    private int id;
    private int listId;
    public ResponseCodeList() {
    }


    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public String getDogImageUrl() {
		return dogImageUrl;
	}

	public void setDogImageUrl(String dogImageUrl) {
		this.dogImageUrl = dogImageUrl;
	}

	public ResponseCodeList(int code, String description, String imageUrl) {
        this.code = code;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
