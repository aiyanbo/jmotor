package jmotor.web.i18n.tag;

import jmotor.core.i18n.I18nManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Component:
 * Description:
 * Date: 12-3-13
 *
 * @author Andy.Ai
 */
public class I18nManagerTag extends SimpleTagSupport {
    private I18nManager i18nManager;
    private String baseNames;
    private String separator;
    private String locale;

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
    }

    public void setBaseNames(String baseNames) {
        this.baseNames = baseNames;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setI18nManager(I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }
}
