package com.mengcraft.ultimatemenu.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemFormat {

    boolean Show_Players_On_Item_Amount;
    boolean Close_On_Click;
    boolean awaysOnline;
    String id;
    int Slot;
    int DATA_Full;
    int ID_Full;
    int DATA_Online;
    int ID_Online;
    int DATA_Offline;
    int ID_Offline;
    ArrayList commands = new ArrayList();
    ArrayList onlineNames = new ArrayList();
    ArrayList<String> motdFullNames = new ArrayList<>();
    int onlineFrameNames = 0;
    ArrayList<List<String>> onlineMotd = new ArrayList<>();
    int onlineFrame = 0;
    ArrayList offlineNames = new ArrayList();
    int offlineFrameNames = 0;
    ArrayList offlineMotd = new ArrayList();
    int offlineFrame = 0;
    ArrayList FullNames = new ArrayList();
    int fullFrameNames = 0;
    ArrayList FullMotd = new ArrayList();
    int fullFrame = 0;
    int idMotdFull;
    int dataMotdFull;
    String motdField;

}