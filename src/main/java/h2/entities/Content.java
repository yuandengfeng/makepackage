package h2.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Content")
public class Content {

    @DatabaseField(id = true,width = 10000)
    private String WENSHUID;  //docno

    @DatabaseField(dataType = DataType.LONG_STRING)
    private String content;   //content

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    @DatabaseField(width = 10000)
    private String publish_time;  //docno

    public String getWENSHUID() {
        return WENSHUID;
    }

    public void setWENSHUID(String WENSHUID) {
        this.WENSHUID = WENSHUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
