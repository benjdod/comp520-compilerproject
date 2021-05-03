  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L14
  4         HALT   (0)   
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L10
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         CALL         puteol  
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
 34  L11:   PUSH         1
 35         LOADL        0
 36         LOAD         -1[LB]
 37         CALL         arraylen
 38         STORE        3[LB]
 39  L12:   LOAD         3[LB]
 40         LOAD         4[LB]
 41         CALL         sub     
 42         JUMPIF (0)   L13
 43         LOAD         -1[LB]
 44         LOAD         4[LB]
 45         CALL         arrayref
 46         CALL         put     
 47         LOAD         4[LB]
 48         CALL         succ    
 49         STORE        4[LB]
 50         JUMP         L12
 51  L13:   CALL         puteol  
 52         RETURN (0)   1
 53  L14:   PUSH         1
 54         LOADL        5
 55         CALL         newarr  
 56         LOAD         -1[ST]
 57         LOADL        0
 58         LOADL        104
 59         CALL         arrayupd
 60         LOAD         -1[ST]
 61         LOADL        1
 62         LOADL        101
 63         CALL         arrayupd
 64         LOAD         -1[ST]
 65         LOADL        2
 66         LOADL        108
 67         CALL         arrayupd
 68         LOAD         -1[ST]
 69         LOADL        3
 70         LOADL        108
 71         CALL         arrayupd
 72         LOAD         -1[ST]
 73         LOADL        4
 74         LOADL        111
 75         CALL         arrayupd
 76         STORE        3[LB]
 77         LOAD         3[LB]
 78         CALL         L11
 79         PUSH         1
 80         LOAD         3[LB]
 81         STORE        4[LB]
 82         LOAD         4[LB]
 83         CALL         L11
 84         RETURN (0)   1
