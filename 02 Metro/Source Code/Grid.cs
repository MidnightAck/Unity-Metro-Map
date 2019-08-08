using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Grid : MonoBehaviour {

	public Vector2 gridSize;
	private Node[,] grid;
	public float nodeRadius;
	public float nodeDiameter;
	public LayerMask whatLayer;
	public int gridCntX, gridCntY; //格子两个方向上的node数量
	public Transform player;
	public List<Node> path;

	void Awake(){
		path = new List<Node> ();

		nodeDiameter = nodeRadius * 2;

		gridCntX = Mathf.RoundToInt(gridSize.x / nodeDiameter);
		gridCntY = Mathf.RoundToInt(gridSize.y / nodeDiameter);

		grid = new Node[gridCntX, gridCntY];

		creatGrid ();
	}

	void OnDrawGizmos(){
		Gizmos.DrawWireCube (transform.position, new Vector3 (gridSize.x, 1, gridSize.y));

		if (grid == null)
			return;

		foreach (var node in grid) {
			Gizmos.color = node.walkable ? Color.white : Color.red;
			Gizmos.DrawCube (node.worldPos, Vector3.one * (nodeDiameter - 0.1f));
		}

		if (path != null) {
			foreach (var node in path) {
				Gizmos.color = Color.black;
				Gizmos.DrawCube (node.worldPos, Vector3.one * (nodeDiameter - 0.1f));
				Debug.Log (node.gridX + " " + node.gridY);
			}
		}

		Node playerNode = GetFromPosition (player.position);
		if (playerNode != null && playerNode.walkable) {
			Gizmos.color = Color.black;
			Gizmos.DrawCube (playerNode.worldPos, Vector3.one * (nodeDiameter - 0.1f));
		}
	}


	private void creatGrid(){
		Vector3 startPoint = transform.position - (gridSize.x / 2) * Vector3.right - (gridSize.y / 2) * Vector3.forward;

		for (int i = 0; i < gridCntX; i++) {
			for (int j = 0; j < gridCntY; j++) {
				Vector3 worPos = startPoint + 
					Vector3.right * (i * nodeDiameter + nodeRadius) + Vector3.forward * (j * nodeDiameter + nodeRadius);

				bool walkable = !Physics.CheckSphere (worPos, nodeRadius, whatLayer);

				grid [i, j] = new Node (walkable, worPos, i, j);
			}
		}
	}

	public Node GetFromPosition(Vector3 pos){
		float percentX = (pos.x + gridSize.x / 2) / gridSize.x;
		float percentY = (pos.z + gridSize.y / 2) / gridSize.y;

		percentX = Mathf.Clamp01 (percentX);
		percentY = Mathf.Clamp01 (percentY);

		int x = Mathf.RoundToInt ((gridCntX - 1) * percentX);
		int y = Mathf.RoundToInt ((gridCntY - 1) * percentY);

		return grid [x, y];
	}

	public List<Node> GetNeibourhood(Node node){
		List<Node> neibourhood = new List<Node> ();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				int tempX = node.gridX + i;
				int tempY = node.gridY + j;

				if (tempX < gridCntX && !(tempX < 0) && !(tempY < 0) && tempY < gridCntY)
					neibourhood.Add (grid [tempX, tempY]);
			}
		}

		return neibourhood;
	}
}
