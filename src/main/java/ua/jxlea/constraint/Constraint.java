package ua.jxlea.constraint;

import lombok.Data;

@Data
public class Constraint {
    private String id;
    private String jaloCode;
    private String active;
    private String activeForStorefront;
    private String annotation;
    private String type;
    private String descriptor;
    private DefaultMessage defaultMessage;
    private String flags;
    private Message message;
    private String needReload;
    private String qualifier;
    private String regexp;
    private String severity;
    private String target;
    private String fieldCompare;
    private String valuesCompare;
}
