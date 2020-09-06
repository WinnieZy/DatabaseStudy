package com.github.winniezy.database.upgrade;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

// Xml文件根节点
public class UpgradeXml {

    private List<UpgradeStep> upgradeSteps;

    public UpgradeXml(Document document){
        //获取升级的脚本，解析根节点
        NodeList upgradeSteps = document.getElementsByTagName("upgradeStep");
        this.upgradeSteps = new ArrayList<>();
        for (int i = 0; i < upgradeSteps.getLength(); i++) {
            Element element = (Element) upgradeSteps.item(i);
            UpgradeStep upgradeStep = new UpgradeStep(element);
            this.upgradeSteps.add(upgradeStep);
        }
    }

    public List<UpgradeStep> getUpgradeSteps(){
        return upgradeSteps;
    }
}
