# assembler
This is a class project for System Software course.
The course project was broken down into separate projects to write pass 1 and pass 2 assembler for SIC.

Pass 1 is to define symbols:
1) Assign addresses to all statements in the program.
2) Save values (addresses) assigned to all labels for use in Pass 2.
3) Perform some processing of assembler directives. 
(This includes processiong that affects address assignment, such as determining the length of data areas defined by BYTE, RESW, etc.).

Pass 2 is to assemble instructions and generate object program:
1) Assemble instructions (translating operation codes and looking up addresses).
2) Generate data values defined by BYTE, WORD, etc.
3) Perform processing of assembler directives not done during Pass 1.
4) Write the object program and the assembly listing.

I ended up not needing to do Pass 2, but I am planning on doing it sometime in the near future.

