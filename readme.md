## 数据结构课程设计总结

[github-CrowFea](https://github.com/CrowFea/DataStructureDesgin)

### 第一部分 算法实现设计说明
#### 题目
试从空树出发构造一棵深度至少为3(不包括失误结点)的3阶B-树(又称2-3树),并可以随时进行查找、插入、删除等操作
要求:能够把构造和删除过程中的B-树随时显示输出来,能给出查找是否成功的有关信息。

#### 软件功能
软件主要实现的是B-树的增删查操作，并且要将整个过程可视化的呈现出来，具体的功能可以分为以下几个部分：
- **B-树的基本操作**

包括B-树的构建、添加节点、删除节点、查找值。B-树的基本操作也是数据结构设计的一部分，以数据结构的设计为主，实现方法在介绍逻辑结构时介绍。
- **可视化图形显示**

包括B-树的结构显示，以及前端后端的交互。在这里本文选择Java窗体程序完成前端，由于操作输入都不复杂，操作的数据量也不大，直接在程序内进行数据存储，不添加额外数据库。

#### 操作说明
 
- 输入框key：输入节点的key值。节点为key-value形式，key为浮点数。
- 输入框element：输入节点的element值。浮点数。
- 插入按钮：输入数据后点击insert进行插入操作。
- 删除按钮：输入key值后可以检索完成删除。
- 查询按钮：输入key值后可以查询该值是否存在，结果显示在左侧的Find结果中。
- 前一步：可以回调查看操作的前一步树的结构。
- 后一步：在回调中查看操作的下一步结果。

插入

![insert](https://media.giphy.com/media/h5i0a624X2AmwQMfMZ/giphy.gif)

删除

![del](https://media.giphy.com/media/eJF2WOmSQxrTsOx7WC/giphy.gif)

查询

![find](https://media.giphy.com/media/fwK5wwDM8ojOeQdpQl/giphy.gif)


 
### 综合应用设计说明

#### 题目
上海的地铁交通网络已基本成型上海的地铁交通网路已基本成型,建成的地铁线十多条,站点上百个,现需建立一个换乘指南打印系统,通过输入起点站和终点站,打印出地铁换乘指南,指南内容包括起点站,换乘站,终点站.

- 图形化显示地铁网络结构,能动态添加地铁线路和地铁站点

- 根据输入起点站和终点站,显示地铁换乘指南.

- 通过图形界面显示乘车路径

#### 软件功能
软件可实现的功能如下：
- **图形化显示上海地铁**
    本文选取了上海地铁的1-13号线。在unity中使用ProBuilder插件进行level Design。制作了三维的上海地铁图。
- **进行视角的转换和摄像机控制**。

    Unity采用摄像机作为视角入口，由于场景复杂且略显庞大，在使用程序时需要对视角进行调整和转换。需要编写脚本对摄像机进行控制。
- **两种循迹模式**

    本程序完成了两种循迹模式：
    一种是通过在UI界面输入起点终点完成地铁换乘和循迹；一种是鼠标直接点击，玩家从当前位置寻找最短路径到达终点。
- **最短路径循迹**

    无论在哪一种操作模式下，玩家都需要找到最短的到达终点的路。因为在level design时比例尺不能完全和现实情况下的地铁相同。可视化中的图形长度仅为表示，真正的站与站之间的长度为脚本程序中定义的长度。
- **简洁友好的UI界面**

    玩家需要一个简洁友好，尽量美观的UI进行交互。UI界面可以提供输入（包括起点终点），输出换乘策略等。
   
#### 操作说明
- **相机控制**：
    - 鼠标左键：按住进行平移
    - 鼠标右键：按钮进行以视角中点的旋转
    - 鼠标滚轮：进行以视角中点的缩放
    - 方向键上下左右：进行视角上下左右的平移
- **角色控制**：
    - 输入起点：输入开始的地铁站。注意这里的输入输出均为地铁站首字母大写。如枫桥路即为（FQL）。
    - 输入终点：输入结束的地铁站。注意这里的输入输出均为地铁站首字母大写。如枫桥路即为（FQL）。
    - 循迹按钮：输入完成之后点击该按钮进行循迹。
    - 换乘输出：点击循迹按钮之后，此处会显示换乘的方式。
    - 退出程序：点击该按钮会退出程序。
    - 游戏世界：点击任意位置小球会从当前位置前往点击位置，在点击循迹按钮之后小球会自动沿着最短路径前进。


视角转换

![rotate](https://media.giphy.com/media/VDBegeubEb0rMxlyVJ/giphy.gif)

输入

![enter](https://media.giphy.com/media/cPNWLoXm2oljS7m30f/giphy.gif)

循迹

![enter](https://media.giphy.com/media/l3JEGaccyhW3m1OW68/giphy.gif)

 
第四部分 参考文献
- [1]. Thomas H.Cormen、Charles E.Leiserson, Introduction to Algorithms. The MIT Press,  2nd edition (September 1, 2001)
- [2]. Peter Shirley, Steve Marschner, Fundamentals Of Computer Graphics,
- [3]. Peter Shirley, Ray Tracing_ The Next Week
- [4]. Introduction to 3D Game Programming with DirectX 11
