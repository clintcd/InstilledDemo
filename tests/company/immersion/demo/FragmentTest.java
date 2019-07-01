package company.immersion.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FragmentTest {

    private static final long POSITION = 20;
    private static final long LENGTH = 100;
    private static final String NAME = "testName";
    private static final String TYPE = "testType";
    private static final String UUID = "testUUID";
    private static final String TIMESTAMP = "testTimestamp";


    private Fragment fragment = null;


    @BeforeEach
    void init() {
        fragment = new Fragment(POSITION, LENGTH);
    }

    @Test
    void getPosition() {
        assertEquals(POSITION, fragment.getPosition());
    }

    @Test
    void setPosition() {
        fragment.setPosition(POSITION+1);
        assertEquals(POSITION+1, fragment.getPosition());
    }

    @Test
    void getLength() {
        assertEquals(LENGTH, fragment.getLength());
    }

    @Test
    void setLength() {
        fragment.setLength(LENGTH+1);
        assertEquals(LENGTH+1, fragment.getLength());
    }

    @Test
    void getName() {
        fragment.setName(NAME);
        assertEquals(NAME, fragment.getName());
    }

    @Test
    void setName() {
        fragment.setName(NAME);
        assertEquals(NAME, fragment.getName());
    }

    @Test
    void getType() {
        fragment.setType(TYPE);
        assertEquals(TYPE, fragment.getType());
    }

    @Test
    void setType() {
        fragment.setType(TYPE);
        assertEquals(TYPE, fragment.getType());
    }

    @Test
    void getUuid() {
        fragment.setUuid(UUID);
        assertEquals(UUID, fragment.getUuid());
    }

    @Test
    void setUuid() {
        fragment.setUuid(UUID);
        assertEquals(UUID, fragment.getUuid());
    }

    @Test
    void getTimestamp() {
        fragment.setTimestamp(TIMESTAMP);
        assertEquals(TIMESTAMP, fragment.getTimestamp());
    }

    @Test
    void setTimestamp() {
        fragment.setTimestamp(TIMESTAMP);
        assertEquals(TIMESTAMP, fragment.getTimestamp());
    }
}