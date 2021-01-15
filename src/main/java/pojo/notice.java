package pojo;

import java.util.List;

/**
 * 通知正文的结构
 */
public class notice {
    // 描述
    String description;
    // 内容
    String text;
    // 子标题
    List<notice> subtitle;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<notice> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(List<notice> subtitle) {
        this.subtitle = subtitle;
    }
}
