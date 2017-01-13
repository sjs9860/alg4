Deque and Randomized Queue implementations are required to use at most 48N + 192 bytes

Typical memory usage for primitive types and arrays

type	byte
boolean	1
byte	1
char	2
int		4
float	4
double	8
long	8

char[]	2N + 24
int[]	4N + 24
double[]	8N + 24

char[][]	~2MN
int[]][]	~4MN
double[][]	~8MN

Memory usage for objects

object overhead	16
reference		8
padding			each object uses a multiply of 8 bytes 