package Interface;
import Class.claim;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public interface ClaimProcessManager {
    void add(Scanner scanner) throws ParseException;
    void update(Scanner scanner);
    void delete(Scanner scanner);
    claim getOne(Scanner scanner);
    List<claim> getAll();
}