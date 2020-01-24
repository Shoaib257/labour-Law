package com.example.labourlaw;

import androidx.annotation.Nullable;
import androidx.annotation.TransitionRes;

import com.google.firebase.database.core.utilities.Tree;

import java.util.ArrayList;


public class TreeDataStructureForLaw {
    class Node{
        //userdata holder
        private final Object data;
        private Node(Object object, int nodeId, int parent){
            this.data=object;
            this.nodeId = nodeId;
            this.parent = parent;
        }
        //easy to use methods
        Node addChild(Object object){
            return TreeDataStructureForLaw.this.addChild(object,this);
        }
        Node update(Object object){
            return TreeDataStructureForLaw.this.updateNode(object,this);
        }
        ArrayList<Node> getChildren(){
            return TreeDataStructureForLaw.this.getChildren(this);
        }

        ArrayList<Node> addListOfChild(ArrayList<Object> list){
            return TreeDataStructureForLaw.this.addListOfChild(list,this);
        }

        //internal calculation variables
        //int level=0;
        private final int nodeId;
        private final int parent;
    }

    ArrayList<Node> NODES=new ArrayList<Node>();
    int idAvailableFrom=0;

    private int generateNewNodeID(){
    idAvailableFrom+=1;
        return idAvailableFrom ;
    }


    public Node updateNode(Object updatedData, Node targetNode){
        int index=findNodeIndexInArrayList(targetNode);
        Node arrayNode=NODES.get(index);
        Node newNode=new Node(updatedData, arrayNode.nodeId, arrayNode.parent);
        NODES.remove(index);
        NODES.add(newNode);
        return newNode;
    }


    public int findNodeIndexInArrayList(Node targetNode){

        for(int i=0;i<NODES.size();i++){
            if(targetNode.nodeId==NODES.get(i).nodeId){
            return i;
            }
        }
        //throw new NodeDoesNotExists("Node does not exists on the Tree. ");

        //Node does not exists
        return 99999;
    }

    public void clearAll(){
        NODES.clear();
    }

    /**
     * Pass any Object into #childNode parameter to add a node. parent node
     * indicates the parent which will contain childs.
     * @param childNode
     * @param parent
     */

    public Node addChild(@Nullable Object childNode,@Nullable Node parent ){
            int newNodeID=generateNewNodeID();
            int parentNodeId=parent.nodeId;
            Node newNode=new Node(childNode, newNodeID, parentNodeId);
            NODES.add(newNode);
            return newNode;
    }

    public ArrayList<Node> addListOfChild(ArrayList<Object> list,Node parent){
        ArrayList<Node> childAdded=new ArrayList<Node>();
        for (Object childNode:list){
            int newNodeID=generateNewNodeID();
            int parentNodeId=parent.nodeId;
            Node newNode=new Node(childNode, newNodeID, parentNodeId);
            NODES.add(newNode);
            childAdded.add(newNode);
        }
        return childAdded;
    }

    public Node addRootNode(@CantBeNull Object node){
            if(NODES.size()==0){
                idAvailableFrom=0;
                int newNodeID=generateNewNodeID();
                Node newNode=new Node(node, newNodeID, 0);
                NODES.add(newNode);
                return newNode;

            }else{
                ArrayList list=getRootNodeWithArrayListIndex(); //includes Node and Node[0] index[1] of the Storage Arraylist
                int preRootNodeIndex=Integer.parseInt(list.get(1).toString());
                Node preRootNode=(Node)list.get(0);
                Node newRootNode=new Node(node, generateNewNodeID(), 0);
                Node preRootNodeRecreate=new Node(preRootNode.data,preRootNode.nodeId, newRootNode.nodeId);
                NODES.remove(preRootNodeIndex);
                NODES.add(preRootNodeRecreate);
                NODES.add(newRootNode);
                return newRootNode;
            }


    }

    public boolean hasChild(@CantBeNull Node node){
        //if the list is empty that means hasChild true. Else false
        return getChildren(node).size()!=0;
    }

    public ArrayList<Node> getChildren(@MustHaveChildren @CantBeNull Node node){
        ArrayList<Node> childrens=new ArrayList<Node>();
        if(NODES.size()!=0){
            int nodeId=node.nodeId;
            for(Node node1:NODES){
                if(node1.parent==nodeId){
                    childrens.add(node1);
                }
            }
        }
        return childrens;
    }

    public int getChildrenCountOf( @CantBeNull Node node){
        int childrenCount=0;
        int nodeId=node.nodeId;
        for(Node node1:NODES){
            if(node1.parent==nodeId){
                childrenCount++;
            }
        }

        return childrenCount;
    }

/*    public int getDepthOf(@CantBeNull Object node){

        return 0;
    }

    public int getLevelOf(@CantBeNull Object node){

        return 0;
    }*/

    //internal call
   private ArrayList getRootNodeWithArrayListIndex(){
        ArrayList list=new ArrayList();
        for(int i=0;i<NODES.size();i++){
            if(NODES.get(i).parent==0){
                list.add(NODES.get(i));
                list.add(""+i);
                return list;
            }
        }



    return null;
    }

    /**
     * returns the root node of the tree. If the tree does not have any node then null is returned.
     * @return
     */
    private Node getRootNode(){

        for(int i=0;i<NODES.size();i++){
            if(NODES.get(i).parent==0){
                return NODES.get(i);
            }
        }


    return null;
    }

    public static void main (String[] args ) {
        TreeDataStructureForLaw tree=new TreeDataStructureForLaw();
        tree.addRootNode("Root: 0").addChild("Child1: ROOT").addChild("Child2:Child1").update("Child2:child1");
        System.out.println(tree.getRootNode().getChildren().get(0).getChildren().get(0).data.toString());

    }





}
