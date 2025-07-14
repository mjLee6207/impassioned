package egovframework.example.data.service;

import java.util.List;

public interface DataManager {
    void execute();
    List<DataVO> fetch();
}
