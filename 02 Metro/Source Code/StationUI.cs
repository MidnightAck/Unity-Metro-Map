using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
public class StationUI : MonoBehaviour
{

    public Text nameLabel;
    // Update is called once per frame
    void Update()
    {
        Vector3 namePos = Camera.main.WorldToScreenPoint(this.transform.position);
        namePos.z = 0;//namePos.z如果不为0，则有可能覆盖 其他的UI
        nameLabel.transform.position = namePos;
    }


}
