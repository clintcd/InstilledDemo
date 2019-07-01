package company.immersion.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The Fragment class encapsulates the information about a viewed clip of a video.
 *
 */
public class Fragment {

    private long position;      // The position in a video where viewing began
    private long length;        // The total amount of video viewed for clip
    private String name;        // Name of clip
    private String type;        // Type of clip
    private String uuid;        // Unique identifier of this viewing session
    private String timestamp;  // the creation date of this fragment (right after viewing)

    /**
     * Constructor for Fragment class.
     *
     * @param position
     * @param length
     */
    public Fragment(long position, long length) {
        this.position = position;
        this.length = length;
        this.name = "defaultName";
        this.type = "standard";
        this.uuid = UUID.randomUUID().toString();
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    /**
     * Returns the position.
     *
     * @return
     */
    public long getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position
     */
    public void setPosition(long position) {
        this.position = position;
    }

    /**
     * Returns the length.
     *
     * @return
     */
    public long getLength() {
        return length;
    }

    /**
     * Sets the length.
     *
     * @param length
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * Returns the name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the type.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the UUID.
     *
     * @return
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the UUID.
     *
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Returns the timestamp.
     *
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
