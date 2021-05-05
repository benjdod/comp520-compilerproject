  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5  L11:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L12
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L12:   LOADL        102
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
 33         LOAD         -1[LB]
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L13:   PUSH         1
 40         LOADL        6
 41         STORE        3[LB]
 42         PUSH         1
 43         LOAD         3[LB]
 44         LOADL        8
 45         CALL         lt      
 46         JUMPIF (0)   L18
 47         LOAD         3[LB]
 48         LOADL        4
 49         CALL         gt      
 50         JUMPIF (0)   L16
 51         LOAD         3[LB]
 52         LOADL        6
 53         CALL         le      
 54         JUMPIF (0)   L14
 55         LOAD         3[LB]
 56         LOADL        1
 57         CALL         add     
 58         JUMP         L15
 59  L14:   LOADL        10
 60  L15:   JUMP         L17
 61  L16:   LOADL        0
 62  L17:   JUMP         L20
 63  L18:   LOAD         3[LB]
 64         LOADL        5
 65         CALL         neg     
 66         CALL         lt      
 67         JUMPIF (0)   L19
 68         LOADL        6
 69         JUMP         L20
 70  L19:   LOADL        2700
 71  L20:   STORE        4[LB]
 72         LOAD         4[LB]
 73         CALL         L11
 74         RETURN (0)   1
