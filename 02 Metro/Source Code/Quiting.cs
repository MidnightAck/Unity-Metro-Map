using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Quiting : MonoBehaviour
{
    public void qiuting()
    {
    #if UNITY_EDITOR
            UnityEditor.EditorApplication.isPlaying = false;
    #else
    Application.Quit();
    #endif
    }
}

