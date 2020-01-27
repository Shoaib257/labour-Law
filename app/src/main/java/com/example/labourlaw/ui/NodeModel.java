package com.example.labourlaw.ui;

import com.example.labourlaw.TreeDataStructure;

public class NodeModel {
    public static final int COMPLETE=0;
    public static final int PENDING=1;
    public static final int PROCESSING=2;
    public TreeDataStructure.Node node;
    public int tag;
    public int childCount;
}
