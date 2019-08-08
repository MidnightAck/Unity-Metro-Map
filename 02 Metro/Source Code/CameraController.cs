using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour
{
    // 模型
    public Transform model;
    // 旋转速度
    public float rotateSpeed = 32f;
    public float rotateLerp = 8;
    // 移动速度
    public float moveSpeed = 1f;
    public float moveLerp = 10f;
    // 镜头拉伸速度
    public float zoomSpeed = 10f;
    public float zoomLerp = 4f;

    // 计算移动
    private Vector3 position, targetPosition;
    // 计算旋转
    private Quaternion rotation, targetRotation;
    // 计算距离
    private float distance, targetDistance;
    // 默认距离
    private const float default_distance = 5f;
    // y轴旋转范围
    private const float min_angle_y = -89f;
    private const float max_angle_y = 89f;


    // Use this for initialization
    void Start()
    {

        // 旋转归零
        targetRotation = Quaternion.identity;
        // 初始位置是模型
        targetPosition = model.position;
        // 初始镜头拉伸
        targetDistance = default_distance;
    }

    // Update is called once per frame
    void Update()
    {
        float dx = Input.GetAxis("Mouse X");
        float dy = Input.GetAxis("Mouse Y");

        // 异常波动
        if (Mathf.Abs(dx) > 5f || Mathf.Abs(dy) > 5f)
        {
            return;
        }

        float d_target_distance = targetDistance;
        if (d_target_distance < 2f)
        {
            d_target_distance = 2f;
        }

        // 鼠标左键移动
        if (Input.GetMouseButton(0))
        {
            dx *= moveSpeed * d_target_distance / default_distance;
            dy *= moveSpeed * d_target_distance / default_distance;
            targetPosition -= transform.up * dy + transform.right * dx;
        }

        // 鼠标右键旋转
        if (Input.GetMouseButton(1))
        {
            dx *= rotateSpeed;
            dy *= rotateSpeed;
            if (Mathf.Abs(dx) > 0 || Mathf.Abs(dy) > 0)
            {
                // 获取摄像机欧拉角
                Vector3 angles = transform.rotation.eulerAngles;
                // 欧拉角表示按照坐标顺序旋转，比如angles.x=30，表示按x轴旋转30°，dy改变引起x轴的变化
                angles.x = Mathf.Repeat(angles.x + 180f, 360f) - 180f;
                angles.y += dx;
                angles.x -= dy;
                angles.x = ClampAngle(angles.x, min_angle_y, max_angle_y);
                // 计算摄像头旋转
                targetRotation.eulerAngles = new Vector3(angles.x, angles.y, 0);
                // 随着旋转，摄像头位置自动恢复
                Vector3 temp_position =
                        Vector3.Lerp(targetPosition, model.position, Time.deltaTime * moveLerp);
                targetPosition = Vector3.Lerp(targetPosition, temp_position, Time.deltaTime * moveLerp);
            }
        }

        // 上移
        if (Input.GetKey(KeyCode.UpArrow))
        {
            targetPosition -= transform.up * d_target_distance / (2f * default_distance);
        }

        // 下移
        if (Input.GetKey(KeyCode.DownArrow))
        {
            targetPosition += transform.up * d_target_distance / (2f * default_distance);
        }

        // 左移
        if (Input.GetKey(KeyCode.LeftArrow))
        {
            targetPosition += transform.right * d_target_distance / (2f * default_distance);
        }

        // 右移
        if (Input.GetKey(KeyCode.RightArrow))
        {
            targetPosition -= transform.right * d_target_distance / (2f * default_distance);
        }

        // 鼠标滚轮拉伸
        targetDistance -= Input.GetAxis("Mouse ScrollWheel") * zoomSpeed;
    }

    // 控制旋转角度范围：min max
    float ClampAngle(float angle, float min, float max)
    {
        // 控制旋转角度不超过360
        if (angle < -360f) angle += 360f;
        if (angle > 360f) angle -= 360f;
        return Mathf.Clamp(angle, min, max);
    }

    private void FixedUpdate()
    {
        rotation = Quaternion.Slerp(rotation, targetRotation, Time.deltaTime * rotateLerp);
        position = Vector3.Lerp(position, targetPosition, Time.deltaTime * moveLerp);
        distance = Mathf.Lerp(distance, targetDistance, Time.deltaTime * zoomLerp);
        // 设置摄像头旋转
        transform.rotation = rotation;
        // 设置摄像头位置
        transform.position = position - rotation * new Vector3(0, 0, distance);
    }
}