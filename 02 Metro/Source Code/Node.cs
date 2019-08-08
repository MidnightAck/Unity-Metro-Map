using UnityEngine;
using System.Collections;

public class Node{
	//节点索引
	public int gridX,gridY;

	//能否通过
	public bool walkable;

	//节点位置
	public Vector3 worldPos;

	//评估参数
	public int gCost;
	public int hCost;
	public int fCost{
		get { return gCost + hCost; }
	}

	//寻路结束后回溯用
	public Node parent;

	public Node(bool isWall, Vector3 pos,int x,int y){
        hCost = 0;
        gCost = 0;
		this.walkable = isWall;
		this.worldPos = pos;
		this.gridX = x;
		this.gridY = y;
	}
}
