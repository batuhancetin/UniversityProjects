% batuhan cetin
% 2019400117
% compiling: yes
% complete: yes
:- ['cmpecraft.pro'].

:- init_from_map.

% 10 points
% manhattan_distance(+A, +B, -Distance) :- .
% 10 points
% minimum_of_list(+List, -Minimum) :- .
% 10 points
% find_nearest_type(+State, +ObjectType, -ObjKey, -Object, -Distance) :- .
% 10 points
% navigate_to(+State, +X, +Y, -ActionList, +DepthLimit) :- .
% 10 points
% chop_nearest_tree(+State, -ActionList) :- .
% 10 points
% mine_nearest_stone(+State, -ActionList) :- .
% 10 points
% gather_nearest_food(+State, -ActionList) :- .
% 10 points
% collect_requirements(+State, +ItemType, -ActionList) :- .
% 5 points
% find_castle_location(+State, -XMin, -YMin, -XMax, -YMax) :- .
% 15 points
% make_castle(+State, -ActionList) :- .

myappend([],X,X).  % base condition for myappend predicate
myappend([X|Y],Z,[X|W]) :- myappend(Y,Z,W). % append two lists and creates a new list

manhattan_distance(List1, List2, Distance) :- nth0(0,List1,X1),nth0(1,List1,Y1),nth0(0,List2,X2),nth0(1,List2,Y2), Distance is abs(X1-X2) + abs(Y1-Y2).

minimum_of_list(List, Minimum) :- min_list(List, Minimum).

find_nearest_type(State,ObjectType,ObjKey,Object,Distance) :- 
    nth0(1,State,Objects),
    nth0(0,State,Agent),
	findall(Key,Objects.Key.type=ObjectType,Bag),
	findall(Distance,(member(X,Bag),manhattan_distance([Agent.x,Agent.y],[Objects.X.x,Objects.X.y],Distance)),List),
	minimum_of_list(List,Distance),
	nth0(Index,List,Distance),
	nth0(Index,Bag,ObjKey),
	get_dict(ObjKey,Objects,Object).

add_goX(0,[]). % base condition for add_goX predicate
add_goX(X,[go_right|List]) :- X>0,!, X1 is X-1, add_goX(X1,List). % it creates a list is made of 'go_right's
add_goX(X,[go_left|List]) :- X<0, X1 is X+1, add_goX(X1,List). % it creates a list is made of 'go_left's
add_goY(0,[]). % base condition for add_goY predicate
add_goY(Y,[go_down|List]) :- Y>0,!,Y1 is Y-1, add_goY(Y1,List). % it creates a list is made of 'go_down's
add_goY(Y,[go_up|List]) :- Y<0, Y1 is Y+1, add_goY(Y1,List). % it creates a list is made of 'go_up's

navigate_to(State,X,Y,ActionList,DepthLimit) :- 
    nth0(0, State, Agent),
    manhattan_distance([Agent.x,Agent.y],[X,Y],Distance),
    DepthLimit >= Distance,
    XX is X-Agent.x,
    YY is Y-Agent.y,
    add_goX(XX,L1),
    add_goY(YY,L2),
    append(L1,L2,ActionList).

chop_nearest_tree(State,ActionList) :-
    nth0(1,State,Objects),
    nth0(0,State,Agent),
    find_nearest_type(State,tree,ObjKey,_,_),
    XX is Objects.ObjKey.x-Agent.x,
    YY is Objects.ObjKey.y-Agent.y,
    add_goX(XX,L1),
    add_goY(YY,L2),
    myappend(L1,L2,MoveList),
    myappend(MoveList,[left_click_c, left_click_c, left_click_c, left_click_c],ActionList).

mine_nearest_stone(State,ActionList) :-
    nth0(1,State,Objects),
    nth0(0,State,Agent),
    find_nearest_type(State,stone,ObjKey,_,_),
    XX is Objects.ObjKey.x-Agent.x,
    YY is Objects.ObjKey.y-Agent.y,
    add_goX(XX,L1),
    add_goY(YY,L2),
    myappend(L1,L2,MoveList),
    myappend(MoveList,[left_click_c, left_click_c, left_click_c, left_click_c],ActionList).

gather_nearest_food(State,ActionList) :-
    nth0(1,State,Objects),
    nth0(0,State,Agent),
    find_nearest_type(State,food,ObjKey,_,_),
    XX is Objects.ObjKey.x-Agent.x,
    YY is Objects.ObjKey.y-Agent.y,
    add_goX(XX,L1),
    add_goY(YY,L2),
    myappend(L1,L2,MoveList),
    myappend(MoveList,[left_click_c],ActionList).

collectstick(State,ActionList) :- % if there are enough logs to craft stick actionlist is empty 
    nth0(0,State,Agent),
    item_info(stick,Reqs,_),
    member(log,Agent.inventory),
    Agent.inventory.log >= Reqs.log,!,
    ActionList is [].


collectstick(State,ActionList) :- % if there aren't enough logs to craft stick actionlist is chop one tree
    chop_nearest_tree(State,ActionList).

collectstone_axe(State,ActionList) :- % if there are enough logs,sticks and cobblestones to craft collectstone_axe actionlist is empty 
    nth0(0,State,Agent),
    item_info(stone_axe,Reqs,_),
    dict_pairs(Agent.inventory,_,Pairs),
    pairs_keys(Pairs, Keys),
    member(stick,Keys),member(log,Keys),member(cobblestone,Keys),Reqs.log =< Agent.inventory.log,Reqs.stick =< Agent.inventory.stick,Reqs.cobblestone =< Agent.inventory.cobblestone,!,
    ActionList = [].

collectstone_axe(State,ActionList) :- % if there aren't enough logs,sticks and cobblestones to craft collectstone_axe actionlist is chop 2 tree and mine stone
    chop_nearest_tree(State,L1),
    execute_actions(State,L1,S2),
    myappend([],L1,X1),
    chop_nearest_tree(S2,L2),
    execute_actions(S2,L2,S3),
    myappend(X1,L2,X2),
    mine_nearest_stone(S3,L3),
    execute_actions(S3,L3,_),
    myappend(X2,L3,X3),
    myappend(X3,[craft_stick],ActionList).



collectstone_pickaxe(State,ActionList) :- % if there are enough logs,sticks and cobblestones to craft collectstone_pickaxe actionlist is empty 
    nth0(0,State,Agent),
    item_info(stone_pickaxe,Reqs,_),
    dict_pairs(Agent.inventory,_,Pairs),
    pairs_keys(Pairs, Keys),
    member(stick,Keys),member(log,Keys),member(cobblestone,Keys),Reqs.log =< Agent.inventory.log,Reqs.stick =< Agent.inventory.stick,Reqs.cobblestone =< Agent.inventory.cobblestone,!,
    ActionList = [].

collectstone_pickaxe(State,ActionList) :- % if there aren't enough logs,sticks and cobblestones to craft collectstone_pickaxe actionlist is empty 
    chop_nearest_tree(State,L1),
    execute_actions(State,L1,S2),
    myappend([],L1,X1),
    chop_nearest_tree(S2,L2),
    execute_actions(S2,L2,S3),
    myappend(X1,L2,X2),
    mine_nearest_stone(S3,L3),
    execute_actions(S3,L3,_),
    myappend(X2,L3,X3),
    myappend(X3,[craft_stick],ActionList).


collect_castle(State,ActionList,State) :- % if there are enough cobblestones to build castle actionlist is empty
    nth0(0,State,Agent),
    member(cobblestone,Agent.inventory),
    Agent.inventory.cobblestone >= 9,!,
    ActionList is [].

collect_castle(State,ActionList,NextState) :- % if there aren't enough cobblestones to build castle actionlist is mine 3 stone
    mine_nearest_stone(State,L1),
    execute_actions(State,L1,S2),
    myappend([],L1,X1),
    mine_nearest_stone(S2,L2),
    execute_actions(S2,L2,S3),
    myappend(X1,L2,X2),
    mine_nearest_stone(S3,L3),
    execute_actions(S3,L3,NextState),
    myappend(X2,L3,ActionList).




collect_requirements(State,ItemType,ActionList) :-
    (ItemType = stick -> collectstick(State,ActionList);
     ItemType = stone_axe -> collectstone_axe(State,ActionList);
     ItemType = stone_pickaxe -> collectstone_pickaxe(State,ActionList)).




file_lines(Lines) :- % this predicate parse map to lines
    setup_call_cleanup(open('map.txt', read, In),
       stream_lines(In, Lines),
       close(In)).

stream_lines(In, Lines) :-
    read_string(In, _, Str),
    split_string(Str, "\n", "", Lines).

remove([_|Tail],Tail).



find_castle_location(State,XMin,YMin,XMax,YMax) :-
    file_lines(L),
    remove(L,L2),
    reverse(L2,[_|X1]), 
    reverse(X1,L3),
    split(L3,Line),
    findall([I,J],(nth0(I,Line,Value),nth0(J,Value,_)),Lines),
    loop(State,Lines,XMin,YMin,XMax,YMax,Line).

make_castle(State,ActionList) :-
    collect_castle(State,List,NextState),
    find_castle_location(NextState,XMin,YMin,_,_),
    nth0(0, NextState, Agent2),
    X1 is XMin+1,
    Y1 is YMin+1,
    XX is X1-Agent2.x,
    YY is Y1-Agent2.y,
    add_goX(XX,L1),
    add_goY(YY,L2),
    myappend(L1,L2,L3),
    myappend(List,L3,L4),
    myappend(L4,[place_nw,place_n,place_ne,place_w,place_c,place_e,place_sw,place_s,place_se],ActionList).


loop(_,[],_,_,_,_,_) :- false. % base condition for loop predicate
loop(State,[Head|Tail],XMin,YMin,XMax,YMax,Line) :- % this predicate find castle locations recursively
    nth0(0,Head,I),
    nth0(1, Head, J),
    width(W),height(H),
    (control(State,I,J,W,H,Line) -> (XMin is J,XMax is J+2,YMax is I+2,YMin is I);loop(State,Tail,XMin,YMin,XMax,YMax,Line)).
    
    


control(State,I,J,W,H,Line) :- % this predicate checks the tile and the tiles around of it to control this place is avaiable for building a castle
    nth0(0,State,_),
    nth0(1,State,Object),
    I > 0,
    J > 0,
    I < H-3,
    J < W-3,
    I1 is I-1,
    I2 is I+1,
    J1 is J-1,
    J2 is J+1,
    matrix(Line,I,J,Value1),matrix(Line,I,J1,Value2),matrix(Line,I,J2,Value3),matrix(Line,I1,J,Value4),matrix(Line,I1,J2,Value5),matrix(Line,I1,J1,Value6),matrix(Line,I2,J,Value7),matrix(Line,I2,J1,Value8),matrix(Line,I2,J2,Value9),
    (Value1='.';(Value1='@',findall(Key,(get_dict(Key,Object,X),X.x = J+1,X.y = I+1),Bag),Bag = [])),(Value2='.';(Value2='@',findall(Key,(get_dict(Key,Object,X),X.x = J1+1,X.y = I+1),Bag),Bag = [])),(Value3='.';(Value3='@',findall(Key,(get_dict(Key,Object,X),X.x = J2+1,X.y = I+1),Bag),Bag = [])),(Value4='.';(Value4='@',findall(Key,(get_dict(Key,Object,X),X.x = J+1,X.y = I1+1),Bag),Bag = [])),(Value5='.';(Value5='@',findall(Key,(get_dict(Key,Object,X),X.x = J2+1,X.y = I1+1),Bag),Bag = [])),(Value6='.';(Value6='@',findall(Key,(get_dict(Key,Object,X),X.x = J1+1,X.y = I1+1),Bag),Bag = [])),(Value7='.';(Value7='@',findall(Key,(get_dict(Key,Object,X),X.x = J+1,X.y = I2+1),Bag),Bag = [])),(Value8='.';(Value1='@',findall(Key,(get_dict(Key,Object,X),X.x = J1+1,X.y = I2+1),Bag),Bag = [])),(Value9='.';(Value9='@',findall(Key,(get_dict(Key,Object,X),X.x = J2+1,X.y = I2+1),Bag),Bag = [])).




split([],[]). % base condition for split predicate
split([Head|L1],[Head2|L2]) :- % this predicate parsing a line to its atoms
    atom_chars(Head, Head2),
    split(L1,L2).

matrix(Matrix, I, J, Value) :- % this predicate returns the value of the specific coordinates of a matrix
    nth0(I, Matrix, Row),
    nth0(J, Row, Value).
    





