using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;
using UnityEngine.UI;

public class PlayerController : MonoBehaviour
{
    public Camera cam;
    public NavMeshAgent agent;

    public InputField from;
    public InputField to;
    GameObject player;

    public void Setget()
    {
       
        GameObject depart = GameObject.Find(from.text);
        GameObject dest = GameObject.Find(to.text);
        if (depart == null || dest == null) return;

        Vector3 length1 = depart.GetComponent<MeshFilter>().mesh.bounds.size;
        float xlength1 = ((length1.x * transform.localScale.x)/2 )+ depart.transform.position.x;
        float ylength1 = length1.y * transform.localScale.y/2 + depart.transform.position.y;
        float zlength1 = -(length1.z * transform.localScale.z)/2 + depart.transform.position.z;
        Vector3 length2 = dest.GetComponent<MeshFilter>().mesh.bounds.size;
        float xlength2 = (length2.x * transform.localScale.x)/2 + dest.transform.position.x;
        float ylength2 = length2.y * transform.localScale.y/2 + dest.transform.position.y;
        float zlength2 = -(length2.z * transform.localScale.z)/2 + dest.transform.position.z;


        Debug.Log(dest.transform.position.x);
        Debug.Log(length2);
        Debug.Log((length2.x * transform.localScale.x) / 2);

        Vector3 departpos = new Vector3(xlength1,ylength1,zlength1);
        Vector3 destpos = new Vector3(xlength2, ylength2, zlength2);
        Debug.Log(destpos);
        inputmove(departpos, destpos);
    }

    public void inputmove(Vector3 departpos, Vector3 destpos)
    {
        player.transform.position = departpos;
        agent.SetDestination(destpos);
    }

    private void Start()
    {
       player=GameObject.Find("Player");
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            Ray ray = cam.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;
        
            if(Physics.Raycast(ray,out hit))
            {
                agent.SetDestination(hit.point);
                Debug.Log(hit.point);
            }
        }
    }
}
