  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L13
  5         HALT   (0)   
  6         LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         CALL         puteol  
 10         RETURN (0)   0
 11         LOAD         -1[LB]
 12         JUMPIF (0)   L10
 13         LOADL        116
 14         CALL         put     
 15         LOADL        114
 16         CALL         put     
 17         LOADL        117
 18         CALL         put     
 19         LOADL        101
 20         CALL         put     
 21         RETURN (0)   1
 22  L10:   LOADL        102
 23         CALL         put     
 24         LOADL        97
 25         CALL         put     
 26         LOADL        108
 27         CALL         put     
 28         LOADL        115
 29         CALL         put     
 30         LOADL        101
 31         CALL         put     
 32         CALL         puteol  
 33         RETURN (0)   1
 34  L11:   LOAD         -1[LB]
 35         JUMPIF (0)   L12
 36         LOAD         -1[LB]
 37         CALL         putarr  
 38         CALL         puteol  
 39         RETURN (0)   1
 40  L12:   LOADL        110
 41         CALL         put     
 42         LOADL        117
 43         CALL         put     
 44         LOADL        108
 45         CALL         put     
 46         LOADL        108
 47         CALL         put     
 48         CALL         puteol  
 49         RETURN (0)   1
 50  L13:   LOADL        0
 51         STORE        1[SB]
 52         LOAD         1[SB]
 53         CALL         L11
 54         LOADL        16
 55         CALL         newarr  
 56         LOAD         -1[ST]
 57         LOADL        0
 58         LOADL        110
 59         CALL         arrayupd
 60         LOAD         -1[ST]
 61         LOADL        1
 62         LOADL        111
 63         CALL         arrayupd
 64         LOAD         -1[ST]
 65         LOADL        2
 66         LOADL        116
 67         CALL         arrayupd
 68         LOAD         -1[ST]
 69         LOADL        3
 70         LOADL        32
 71         CALL         arrayupd
 72         LOAD         -1[ST]
 73         LOADL        4
 74         LOADL        110
 75         CALL         arrayupd
 76         LOAD         -1[ST]
 77         LOADL        5
 78         LOADL        117
 79         CALL         arrayupd
 80         LOAD         -1[ST]
 81         LOADL        6
 82         LOADL        108
 83         CALL         arrayupd
 84         LOAD         -1[ST]
 85         LOADL        7
 86         LOADL        108
 87         CALL         arrayupd
 88         LOAD         -1[ST]
 89         LOADL        8
 90         LOADL        32
 91         CALL         arrayupd
 92         LOAD         -1[ST]
 93         LOADL        9
 94         LOADL        97
 95         CALL         arrayupd
 96         LOAD         -1[ST]
 97         LOADL        10
 98         LOADL        110
 99         CALL         arrayupd
100         LOAD         -1[ST]
101         LOADL        11
102         LOADL        121
103         CALL         arrayupd
104         LOAD         -1[ST]
105         LOADL        12
106         LOADL        109
107         CALL         arrayupd
108         LOAD         -1[ST]
109         LOADL        13
110         LOADL        111
111         CALL         arrayupd
112         LOAD         -1[ST]
113         LOADL        14
114         LOADL        114
115         CALL         arrayupd
116         LOAD         -1[ST]
117         LOADL        15
118         LOADL        101
119         CALL         arrayupd
120         STORE        1[SB]
121         LOAD         1[SB]
122         CALL         L11
123         RETURN (0)   1
