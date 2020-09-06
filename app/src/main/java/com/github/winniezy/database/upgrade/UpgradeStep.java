package com.github.winniezy.database.upgrade;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class UpgradeStep {

    private String versionFrom;
    private String versionTo;
    private List<UpgradeDb> upgradeDbs;
    public UpgradeStep(Element element) {
        versionFrom = element.getAttribute("versionFrom");
        versionTo = element.getAttribute("versionTo");
        this.upgradeDbs = new ArrayList<>();
        NodeList dbs = element.getElementsByTagName("upgradeDb");
        for (int i = 0; i < dbs.getLength(); i++) {
            Element db = (Element) dbs.item(i);
            UpgradeDb upgradeDb = new UpgradeDb(db);
            this.upgradeDbs.add(upgradeDb);
        }
    }

    public String getVersionFrom() {
        return versionFrom;
    }

    public String getVersionTo() {
        return versionTo;
    }

    public List<UpgradeDb> getUpgradeDbs() {
        return upgradeDbs;
    }
}
