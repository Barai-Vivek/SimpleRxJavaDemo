# SimpleRxJavaDemo
In this demo project i have created following activities.
<br><br>

<b><LI>SimpleObserverObservableActivity</b><br>
This activity represents Observer and Observable simple example where a list of task is iterated and filtered.
Then they are displayed in onNext() method.
</LI>

There are <b>four</b>operators who's job is to create Observables.
<OL>create()</OL>
<OL>just()</OL>
<OL>range()</OL>
<OL>repeat()</OL>

<b><LI>ObserverObservableWithCreateOperatorsActivity</b><br>
This activity represents an example of create() operator.
Where in a for loop is executed in background thread and then task is oberved on main thread.
</LI>

<b><LI>ObserverObservableWithJustOperatorsActivity</b><br>
This activity represents an example of just() operator.
 Where just operator accepts maximum array of 10 lenght you can also pass multiple strings upto 10 length.
 In this example is have just passed a single object of task.
</LI>

<b><LI>ObserverObservableWithRangeOperatorsActivity</b><br>
This activity represents an example of range() operator.
 In create() operator i had used a for loop to print all task in main thread but when using range() operator makes it simple i have also used map.</LI>

<b><LI>ObserverObservableWithRepeatOperatorsActivity</b><br>
This activity represents an example of repeat() operator.
Where range is repeated multiple times.
</LI>
