## to-do

基本上有四个模块，一个记录所需要做的事件的app。

* 查看所有事件，包括已完成和正在的，并可以筛选
* 添加新事件
* 完成事件，删除事件，编辑旧事件
* 数据分析



#### 模块细节

1. TasksActivity：连接TasksPresenter和TasksFragment，连接TaskPresenter和TasksRepository()，TasksFragment监听回调TaskPresenter，TaskPresenter逻辑更新UI调用TasksFragment，TasksFragment通过TasksRepository进行数据获取。TasksFragment启动AddEditTaskActivity。

2. AddEditTaskActivity，连接，监听，回调，返回TasksFragment。TasksFragment->TaskDetailActivity
3. TaskDetailActivity：依然结构清晰。返回，TasksActivity->StatisticsActivity
4. StatisticsActivity：数据分析，依然一样的结构。





#### 引用



[MVC，MVP，MVVM](https://mp.weixin.qq.com/s/YRZwtNk03BPi9RKP0bJ3IA​)