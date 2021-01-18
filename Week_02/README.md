# 学习笔记  
## homework  
### hotspot vm gc整理  
#### 回收设计的选择  
> 串行、并行  
>> 串行：即垃圾回收任务是由一个cpu核心全部处理  
>> 并行：垃圾回收任务是会被拆分成若干部分，由多个核心同时处理  
>
>并发、stw  
>> stw：当stw类型的垃圾回收进行时，业务应用会被暂停  
>> 并发：一个或多个垃圾回收任务伴随着业务应用同时进行  
>> 一般来说，并发的垃圾回收器，进行绝大部分的垃圾回收工作是与业务应用并发的进行，同时也会有很少部分的情况下需要stw  
>> 复杂度：并发>stw，因为stw的情况下，对象间的关系也不会变了  
>> 长暂停：并发<stw，因为是多个垃圾回收器并发进行的，更快，但每个垃圾回收器会更复杂，更偏好较大的堆  
>
> 压缩、非压缩、拷贝  
>> 压缩：即将存活对象挪到一起，之外的内存就都变得可用  
>> 非压缩：即原地释放对象，不会把存活对象挪到一起，更快，但是会造成碎片化  
>> 拷贝：将存活对象转移到一个不同的、可以认为是空的内存空间中，拷贝完成后，原地址就可以被当做可用的空间，代价是拷贝行为的花销与额外堆空间的花销  
#### 各类垃圾回收器  
##### 代的设计  
<center>  

![Yg](\.\pics\1YoungG.png)
</center>