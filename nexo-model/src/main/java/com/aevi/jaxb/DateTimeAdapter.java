package com.aevi.jaxb;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime unmarshal(String s) throws Exception {
        return ZonedDateTime.parse(s);
    }

    @Override
    public String marshal(ZonedDateTime zonedDateTime) throws Exception {
        return zonedDateTime == null ? null : zonedDateTime.toString();
    }
}
