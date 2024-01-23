# viewpgae2Demos


viewpage2 嵌套viewpage2 嵌套RecyclerView 的滑动冲突解决方案

思路 
     1 最里面嵌套的RecyclerView 重写TouchEvent 事件 因为 RecyclerView是可以继承的。
     2 viewpage2是无法被改写的 所以里面是采用内部解决滑动冲突解决 外面viewpage2采用外部滑动解决办法。



 1 
 重写 RecyclerView dispatchTouchEvent 函数 判断在竖直滑动的时候 请求上级view 不拦截交给子view处理
 因为我们上级是viewpage2 ，里面有个函数是setUserInputEnabled 所以直接控制他就行了。
 如果在在左右滑动的时候就放开 允许viewpage2可以滑动即可

 2
 新增一个继承类继承任何view子类都可以 或者View
 这个地方的代码参考了之前谷歌官方代码的解决方案 https://www.hi-cat.cn/11798 
 这个博客是集合了 写得比较详细

我通过调试修改了下面逻辑 判断在左右滑动的时候 分方向 从左到右 还是从右到左判断当前viewpage2 
的位置是否是第一个或者最后个。如果是当前可以滑动那么就滑动当前viewpage2.
如果不可以滑动那么就把请求上级拦截事件处理。本身不做任何事件处理了。
 


   



