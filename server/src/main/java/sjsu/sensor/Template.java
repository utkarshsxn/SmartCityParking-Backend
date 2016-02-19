package sjsu.sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utkarshsaxena on 11/30/15.
 */
public interface Template {

    public TemplateModel getTemplate(String id);

    public List<TemplateModel> getAllTemplates();

    public void createTemplate(String id, ArrayList<String> dataType);

    public void deleteTemplate(String templateId);

    public void modifyTemplate(String id, ArrayList<String> newDataType);


}
