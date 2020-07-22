## to-do

基本上有四个模块，一个记录所需要做的事件的app。再加上单元测试代码。

* 查看所有事件，包括已完成和正在的，并可以筛选
* 添加新事件
* 完成事件，删除事件，编辑旧事件
* 数据分析

### MVP

1. 这个框架最大的作用是，单元测试，与统一设计模式较为直观，还有逻辑业务分离。
2. 修改共用接口时问题会很大。
3. 需要人员熟悉MVP方式。

#### 模块细节

1. TasksActivity：连接TasksPresenter和TasksFragment，连接TaskPresenter和TasksRepository()，TasksFragment监听回调TaskPresenter，TaskPresenter逻辑更新UI调用TasksFragment，TasksFragment通过TasksRepository进行数据获取。TasksFragment启动AddEditTaskActivity。

2. AddEditTaskActivity，连接，监听，回调，返回TasksFragment。TasksFragment->TaskDetailActivity
3. TaskDetailActivity：依然结构清晰。返回，TasksActivity->StatisticsActivity
4. StatisticsActivity：数据分析，依然一样的结构。

#### MVP-RXJAVA

* 基本上是以上模块细节，然后使用rxjava优化model和Presenter的连接。

#### MVP-Clean

* 基本上也是分化了model和Presenter的连接。各自交了一些全能给一个新节点，泛型使用比较多，懒得看了。

#### MVP-dagger

* 加入dagger依赖注入框架，主要是通过生成代码的方式，但是居然是运行时注解，有空可以看下源码的处理
* 加入框架最大的特点是，所有的新建对象或者P与Model建立连接，P与V建立连接的过程全部删掉了。不需要通过activity查看关系了。
* 最大的缺点是，不熟悉dagger的话，可读性真的会变得非常非常非常难受。性能上应该不会有大问题。
* 其他基本没有改变
* 熟悉之后应该还可以。
* 分离activity全能，更加清晰把，集中管理。



#### 引用



[MVC，MVP，MVVM](https://mp.weixin.qq.com/s/YRZwtNk03BPi9RKP0bJ3IA​)