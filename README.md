## StickyHeaderDecoration

使用ItemDecoration技术实现的粘性(悬停)头部，用于实现RecyclerView的分组效果。

 <img src="./SVID_20170802_161913.gif" width = "240" height = "360" alt="Screen shoot" align=center />

#### 实现原理
ItemDecoration 有两个绘制时机：`onDraw` 和 `onDrawOver`，其中`onDrawOver`是在item绘制完成之后。  
**RecyclerView本身并没有提供可以固定某个item或者某个canvas的方法。** 
因此要实现粘性头部，只能通过假的视图来完成。

我们当然可以在RecyclerView上贴一个View，然后监听RecyclerView的滚动来实现吸顶效果。具体做法参见[这里](http://www.jianshu.com/p/c596f2e6f587). 但是，这个方案有两个问题：
 * 增加了xml的布局复杂度
 * 每个需要吸顶的页面都要重复一遍fake view + scroll listen的代码，代码重复率高且不易维护

使用 ItemDecoration 技术可以解决这两个问题。

#### 使用方法
首先，你需要将核心类导入到工程中，可以直接复制，但建议使用 gradle 依赖：
```Groovy
    implementation 'org.mym.ui:sticky-header-decoration:0.2.0'
```

接下来，你需要提供一个分组逻辑实现接口，它的定义如下：
```Java
public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {
    //如果某个 item 不需要 header，可返回 NO_HEADER
    long getHeaderId(int childAdapterPosition);
    
    //创建 view 
    @NonNull T onCreateHeaderViewHolder(ViewGroup parent);
    
    //根据 childAdapterPosition 来获取 Header，并绘制对应的内容。
    void onBindHeaderViewHolder(@NonNull T holder, int childAdapterPosition);
}
```

最后，像加分割线一样添加这个 decoration 即可。
```
    recyclerView.addItemDecoration(new StickyHeaderDecoration(headerAdapter));
```

#### API 说明
现在 RecyclerView 的悬停头部 Decoration 网上可以找到不少实现，大多是直接画图，但如果我们需要绘制复杂头部的话，将不得不修改 decoration 
本身的代码，且浪费大量的时间在调试 baseline 和 gravity 参数之类的工作中。我希望定义一个接口，抽象出这个 Header 的绘制，从而将 Decoration 
的核心逻辑固定下来，方便解耦。

考虑到 RecyclerView 的数据可能很多，因此 header 也可能不少，为了让 Header 也可以缓存和复用，我们需要类似于 view holder 的一种对象类型。由于 
Decoration 是搭配 RecyclerView 使用，因此并不用担心引入额外依赖。所以，在 ``StickyHeaderAdapter`` 接口中直接使用了 ViewHolder 
来作为这个中介类型。

#### Licence
```
   Copyright 2017 Muyangmin

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```