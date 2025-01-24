package documentmanagement.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class SearchTextCommand {

    private String text; // 검색 텍스트
}
