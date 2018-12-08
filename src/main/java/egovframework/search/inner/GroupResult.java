package egovframework.search.inner;

import java.util.List;
import java.util.Map;

public class GroupResult {

    private String alias;
    private String aliasText;
    private int count;
    private List<Map<String, String>> documents;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliasText() {
        return aliasText;
    }

    public void setAliasText(String aliasText) {
        this.aliasText = aliasText;
    }

    public List<Map<String, String>> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Map<String, String>> documents) {
        this.documents = documents;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
