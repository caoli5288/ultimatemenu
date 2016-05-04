package com.mengcraft.ultimatemenu.menu;

import java.util.ArrayList;
import java.util.List;

public class ItemInfo {
    boolean Show_Players_On_Item_Amount;
    boolean Close_On_Click;
    boolean forceOnline;
    String id;
    int slot;
    int DATA_Full;
    int ID_Full;
    int DATA_Online;
    int ID_Online;
    int DATA_Offline;
    int ID_Offline;
    ArrayList<String> commandList = new ArrayList<>();
    ArrayList<String> onlineNameList = new ArrayList<>();
    ArrayList<String> motdFullNameList = new ArrayList<>();
    int onlineFrameName = 0;
    ArrayList<List<String>> onlineMotd = new ArrayList<>();
    int onlineFrame = 0;
    ArrayList<String> offlineNameList = new ArrayList<>();
    int offlineFrameName = 0;
    ArrayList<List<String>> offlineMotd = new ArrayList<>();
    int offlineFrame = 0;
    ArrayList<String> fullNameList = new ArrayList<>();
    int fullFrameName = 0;
    ArrayList<List<String>> fullMotd = new ArrayList<>();
    int fullFrame = 0;
    int motdFullId;
    int dataMotdFull;
    String motdFull;
}
