  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L11
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L11:   LOADL        102
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
 33  L12:   LOAD         -1[LB]
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L13:   PUSH         1
 40         LOADL        0
 41         STORE        3[LB]
 42         JUMP         L14
 43  L14:   LOAD         3[LB]
 44         JUMPIF (0)   L16
 45         LOADL        7
 46         CALL         newarr  
 47         LOAD         -1[ST]
 48         LOADL        0
 49         LOADL        101
 50         CALL         arrayupd
 51         LOAD         -1[ST]
 52         LOADL        1
 53         LOADL        120
 54         CALL         arrayupd
 55         LOAD         -1[ST]
 56         LOADL        2
 57         LOADL        105
 58         CALL         arrayupd
 59         LOAD         -1[ST]
 60         LOADL        3
 61         LOADL        116
 62         CALL         arrayupd
 63         LOAD         -1[ST]
 64         LOADL        4
 65         LOADL        105
 66         CALL         arrayupd
 67         LOAD         -1[ST]
 68         LOADL        5
 69         LOADL        110
 70         CALL         arrayupd
 71         LOAD         -1[ST]
 72         LOADL        6
 73         LOADL        103
 74         CALL         arrayupd
 75         CALL         L12
 76         JUMP         L18
 77  L15:   JUMP         L17
 78  L16:   LOADL        9
 79         CALL         newarr  
 80         LOAD         -1[ST]
 81         LOADL        0
 82         LOADL        115
 83         CALL         arrayupd
 84         LOAD         -1[ST]
 85         LOADL        1
 86         LOADL        101
 87         CALL         arrayupd
 88         LOAD         -1[ST]
 89         LOADL        2
 90         LOADL        116
 91         CALL         arrayupd
 92         LOAD         -1[ST]
 93         LOADL        3
 94         LOADL        116
 95         CALL         arrayupd
 96         LOAD         -1[ST]
 97         LOADL        4
 98         LOADL        105
 99         CALL         arrayupd
100         LOAD         -1[ST]
101         LOADL        5
102         LOADL        110
103         CALL         arrayupd
104         LOAD         -1[ST]
105         LOADL        6
106         LOADL        103
107         CALL         arrayupd
108         LOAD         -1[ST]
109         LOADL        7
110         LOADL        32
111         CALL         arrayupd
112         LOAD         -1[ST]
113         LOADL        8
114         LOADL        98
115         CALL         arrayupd
116         CALL         L12
117         LOADL        1
118         STORE        3[LB]
119  L17:   JUMP         L14
120  L18:   POP          0
121         RETURN (0)   1
