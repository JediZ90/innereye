package org.innereye.auto.generator.mbg.plugins;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class MoveExampleClassPlugin extends PluginAdapter {

    private String targetPackage;

    public MoveExampleClassPlugin(){
    }

    public boolean validate(List<String> warnings) {
        targetPackage = properties.getProperty("targetPackage");
        boolean valid = stringHasValue(targetPackage);
        if (!valid) {
            warnings.add(getString("ValidationError.18", "MoveExampleClassPlugin", "targetPackage"));
        }
        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String oldType = introspectedTable.getExampleType();
        String oldPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        introspectedTable.setExampleType(oldType.replaceFirst(oldPackage, targetPackage));
    }
}
