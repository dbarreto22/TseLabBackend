package com.fakenews;

import com.fakenews.impl.SecurityMgr;
import com.fakenews.interfaces.*;

/* @author Romina */
public class ManagersFactory {

    private ManagersFactory() {
    }

    public static ManagersFactory getInstance() {
        return ManagersFactoryHolder.INSTANCE;
    }

    private static class ManagersFactoryHolder {

        private static final ManagersFactory INSTANCE = new ManagersFactory();
    }
 
    public SecurityMgt getSecurityMgt(){
        return new SecurityMgr();
    }
    
}