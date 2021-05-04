  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L18
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   CALL         puteol  
  9         RETURN (0)   0
 10  L12:   LOAD         -1[LB]
 11         JUMPIF (0)   L13
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L13:   LOADL        102
 22         CALL         put     
 23         LOADL        97
 24         CALL         put     
 25         LOADL        108
 26         CALL         put     
 27         LOADL        115
 28         CALL         put     
 29         LOADL        101
 30         CALL         put     
 31         CALL         puteol  
 32         RETURN (0)   1
 33  L14:   LOAD         -1[LB]
 34         JUMPIF (0)   L16
 35         PUSH         1
 36         LOADL        0
 37         LOAD         -1[LB]
 38         CALL         arraylen
 39         STORE        3[LB]
 40  L15:   LOAD         3[LB]
 41         LOAD         4[LB]
 42         CALL         sub     
 43         JUMPIF (0)   L17
 44         LOAD         -1[LB]
 45         LOAD         4[LB]
 46         CALL         arrayref
 47         CALL         put     
 48         CALL         succ    
 49         JUMP         L15
 50  L16:   LOADL        110
 51         CALL         put     
 52         LOADL        117
 53         CALL         put     
 54         LOADL        108
 55         CALL         put     
 56         LOADL        108
 57         CALL         put     
 58  L17:   CALL         puteol  
 59         RETURN (0)   1
 60  L18:   LOADL        6
 61         CALL         newarr  
 62         LOAD         -1[ST]
 63         LOADL        0
 64         LOADL        98
 65         CALL         arrayupd
 66         LOAD         -1[ST]
 67         LOADL        1
 68         LOADL        101
 69         CALL         arrayupd
 70         LOAD         -1[ST]
 71         LOADL        2
 72         LOADL        103
 73         CALL         arrayupd
 74         LOAD         -1[ST]
 75         LOADL        3
 76         LOADL        105
 77         CALL         arrayupd
 78         LOAD         -1[ST]
 79         LOADL        4
 80         LOADL        110
 81         CALL         arrayupd
 82         LOAD         -1[ST]
 83         LOADL        5
 84         LOADL        33
 85         CALL         arrayupd
 86         CALL         L14
 87         CALL         L11
 88         CALL         L11
 89         CALL         L11
 90         LOADL        0
 91         CALL         L12
 92         LOADL        4
 93         CALL         L10
 94         LOADL        1
 95         CALL         L12
 96         CALL         L11
 97         CALL         L11
 98         CALL         L11
 99         CALL         L11
100         LOADL        4
101         CALL         newarr  
102         LOAD         -1[ST]
103         LOADL        0
104         LOADL        101
105         CALL         arrayupd
106         LOAD         -1[ST]
107         LOADL        1
108         LOADL        110
109         CALL         arrayupd
110         LOAD         -1[ST]
111         LOADL        2
112         LOADL        100
113         CALL         arrayupd
114         LOAD         -1[ST]
115         LOADL        3
116         LOADL        33
117         CALL         arrayupd
118         CALL         L14
119         RETURN (0)   1
