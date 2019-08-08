using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class FindPath : MonoBehaviour {
	public Transform player, endPoint;
	private Grid _grid;


	void Awake () {
		_grid = GetComponent<Grid> ();
	}

	void Update () {
		findingPath (player.position, endPoint.position);
	}

	private void findingPath(Vector3 startPos,Vector3 endPos){
		Node startNode = _grid.GetFromPosition (startPos);
		Node endNode = _grid.GetFromPosition (endPos);

		List<Node> openList = new List<Node> ();
		HashSet<Node> closeList = new HashSet<Node> ();
		openList.Add (startNode);

		while (openList.Count > 0) {
            //Step1：找出OpenList里f(n)=g(n)+h(n)最小的node
			Node currentNode = openList [0];
			for (int i = 0; i < openList.Count; i++) {
				if (openList [i].fCost < currentNode.fCost ||
				   openList [i].fCost == currentNode.fCost && openList [i].hCost < currentNode.hCost) 
				{
					currentNode = openList [i];
				}
			}

            //Step2：从OpenList中移除currentNode，并且加入CloseList
			openList.Remove (currentNode);
			closeList.Add (currentNode);

            //Step3：如果currentNode就是最终节点，停止寻路并生成路径
			if (currentNode == endNode) {
				//生成寻路
				generatePath(startNode,endNode);
				return;
			}

            //Step4：遍历currentNode的所有邻居节点
			foreach (var node in _grid.GetNeibourhood(currentNode)) {
				if (!node.walkable || closeList.Contains (node))
					continue;

				int newCont = currentNode.gCost + getDistanceNodes (currentNode, node); // currentNode的g(n) + currentNode到node的估值
                //当OpenList里面没有node 或者 当前算出来的node新g(n)小于OpenList里面的node旧g(n)
				if (newCont < node.gCost || !openList.Contains (node)) {
					node.gCost = newCont;
					node.hCost = getDistanceNodes (node, endNode);
                    //将node的父节点设置为currentNode
					node.parent = currentNode;
                    //如果OpenList没有node则将OpenList加入node
					if (!openList.Contains (node)) {
						openList.Add (node);
					}
				}

			}
		}
	}

    //估价函数 h(n)
	private int getDistanceNodes(Node a,Node b){
		int cntX = Mathf.Abs (a.gridX - b.gridX);
		int cntY = Mathf.Abs (a.gridY - b.gridY);

		return cntX + cntY;
//		if (cntX >= cntY)
//			return 14 * cntY + 10 * (cntX - cntY);
//		else
//			return 14 * cntX + 10 * (cntY - cntX);
	}

	private void generatePath(Node startNode,Node endNode){
		List<Node> path = new List<Node> ();
		Node temp = endNode;

		while (temp != startNode) {
			path.Add (temp);
			temp = temp.parent;
		}

		path.Reverse ();
		_grid.path = path;
	}
}
