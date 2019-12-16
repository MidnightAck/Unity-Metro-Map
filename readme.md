# Unity Metro Map
![coverage](https://img.shields.io/badge/coverage-100%25-brightgreen) ![build](https://img.shields.io/badge/build-passing-brightgreen) ![colorUI](https://img.shields.io/badge/colorUI-v2.0.2-blue)

`Unity Metro Map`是一款使用unity编写，模拟上海地铁的交互程序。用户在输入起止点后，游戏内玩家可自动选择最快路径前往，同时侧边UI栏会打印 相应的地铁换乘指南。同时用户可直接点击屏幕内任意区域，玩家会自动寻址前往。

![enter](https://media.giphy.com/media/l3JEGaccyhW3m1OW68/giphy.gif)

## Table of Contents

- [Feature](#Feature)
- [What Can MetroMap do](#What-Can-MetroMap-do)
- [How to Play](#How-to-Play)
- [what MetroMap looks like](#what-PALS-looks-like)
- [References](#References)


## Feature
上海的地铁交通网络已基本成型上海的地铁交通网路已基本成型,建成的地铁线十多条,站点上百个,现需建立一个换乘指南打印系统,通过输入起点站和终点站,打印出地铁换乘指南,指南内容包括起点站,换乘站,终点站.

- 图形化显示地铁网络结构,能动态添加地铁线路和地铁站点

- 根据输入起点站和终点站,显示地铁换乘指南.

- 通过图形界面显示乘车路径

#### What Can MetroMap do
软件可实现的功能如下：
- **图形化显示上海地铁**
    本文选取了上海地铁的1-13号线。在unity中使用`ProBuilder`插件进行level Design。制作了三维的上海地铁图。
- **进行视角的转换和摄像机控制**。

    Unity采用摄像机作为视角入口，由于场景复杂且略显庞大，在使用程序时需要对视角进行调整和转换。需要编写脚本对摄像机进行控制。
- **两种循迹模式**

    本程序完成了两种循迹模式：
    一种是通过在UI界面输入起点终点完成地铁换乘和循迹；一种是鼠标直接点击，玩家从当前位置寻找最短路径到达终点。
- **最短路径循迹**

    无论在哪一种操作模式下，玩家都需要找到最短的到达终点的路。因为在level design时比例尺不能完全和现实情况下的地铁相同。可视化中的图形长度仅为表示，真正的站与站之间的长度为脚本程序中定义的长度。
- **简洁友好的UI界面**

    玩家需要一个简洁友好，尽量美观的UI进行交互。UI界面可以提供输入（包括起点终点），输出换乘策略等。

 
## How to Play
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


## what MetroMap looks like
视角转换

![rotate](https://media.giphy.com/media/VDBegeubEb0rMxlyVJ/giphy.gif)

输入

![enter](https://media.giphy.com/media/cPNWLoXm2oljS7m30f/giphy.gif)

循迹

![enter](https://media.giphy.com/media/l3JEGaccyhW3m1OW68/giphy.gif)

 
## References
- [1]. Thomas H.Cormen、Charles E.Leiserson, Introduction to Algorithms. The MIT Press,  2nd edition (September 1, 2001)
- [2]. Peter Shirley, Steve Marschner, Fundamentals Of Computer Graphics,
- [3]. Peter Shirley, Ray Tracing_ The Next Week
- [4]. Introduction to 3D Game Programming with DirectX 11
