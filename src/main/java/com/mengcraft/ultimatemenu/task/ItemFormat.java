package com.mengcraft.ultimatemenu.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemFormat {

    boolean Show_Players_On_Item_Amount;
    boolean Close_On_Click;
    boolean awaysOnline;
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
    int onlineFrameNames = 0;
    ArrayList<List<String>> onlineMotd = new ArrayList<>();
    int onlineFrame = 0;
    ArrayList<String> offlineNameList = new ArrayList<>();
    int offlineFrameNames = 0;
    ArrayList<List<String>> offlineMotd = new ArrayList<>();
    int offlineFrame = 0;
    ArrayList<String> fullNameList = new ArrayList<>();
    int fullFrameNames = 0;
    ArrayList<List<String>> fullMotd = new ArrayList<>();
    int fullFrame = 0;
    int idMotdFull;
    int dataMotdFull;
    String motdField;

}
