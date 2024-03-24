/**
 * @author <Nguyen Ha Tuan Nguyen - s3978072>
 */
package Interface;

public interface Id_generate {
    int readLastAssignedId();
    void writeLastAssignedId();
    String generateId();
}
