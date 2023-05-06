(module
(import "runtime" "print" (func $print (param i32)))
(import "runtime" "print" (func $print2 (param f32)))
(import "runtime" "read" (func $read (result i32)))
(import "runtime" "read" (func $read2 (result f32)))
(import "runtime" "exceptionHandler" (func $exception (param i32)))
(memory 2000)
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(start $init)
(func $init
i32.const 100
set_global $SP
call $main
i32.const 0
set_global $SP
)
(func $main
(local $temp i32)
   (local $localsStart i32)
   i32.const 12
   call $reserveStack  ;; returns old MP (dynamic link)
   set_local $temp
   get_global $MP
   get_local $temp
   i32.store
   get_global $MP
   get_global $SP
   i32.store offset=4
   get_global $MP
   i32.const 8
   i32.add
   set_local $localsStart
i32.const 0
get_local $localsStart
i32.add
i32.const 1
i32.store

i32.const 0
i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 1
i32.store

block
loop
i32.const 0
i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 4
i32.const 0
i32.mul
i32.add
i32.load
i32.const 10
i32.lt_s

i32.const 0
get_local $localsStart
i32.add
i32.load
i32.and

i32.eqz
br_if 1
i32.const 0
i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 0
i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 4
i32.const 0
i32.mul
i32.add
i32.load
i32.const 1
i32.add
i32.store

i32.const 0
i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 4
i32.const 0
i32.mul
i32.add
i32.load
i32.const 5
i32.eq
if
i32.const 0
get_local $localsStart
i32.add
i32.const 0
i32.store



end

i32.const 0
i32.const 4
i32.const 0
i32.mul
i32.add

i32.const 4
i32.const 0
i32.mul
i32.add
i32.load
call $print


br 0
end
end

   call $freeStack
i32.const 0

   call $print
)
(func $reserveStack (param $size i32) ;;funcion que reserva memoria para la pila para entrar en un ambito nuevo
   (result i32)
   get_global $MP
   get_global $SP
   set_global $MP
   get_global $SP
   get_local $size
   i32.add
   set_global $SP
   get_global $SP
   get_global $NP
   i32.gt_u
   if
   i32.const 3
   call $exception
   end
)
(func $freeStack ;;funcion que libera la memoria del ultimo ambito en el que se haya entrado
   get_global $MP
   i32.load
   i32.load offset=4
   set_global $SP
   get_global $MP
   i32.load
   set_global $MP   
)
(func $reserveHeap ;;funcion que reserva memoria en el heap para un nuevo ambito
   (param $size i32)
   get_global $NP
   set_global $MP
   get_global $NP
   get_local $size
   i32.add
   set_global $NP
)
(func $freeHeap ;;funcion que libera memoria del heap del ultimo ambito en el que nos encontrasemos
   get_global $MP
   i32.load
   i32.load offset=4
   set_global $NP
   get_global $MP
   i32.load
   set_global $MP   
)
(func $copyn ;; copy $n i32 slots from $src to $dest
   (param $src i32)
   (param $dest i32)
   (param $n i32)
   block
     loop
       get_local $n
       i32.eqz
       br_if 1
       get_local $n
       i32.const 1
       i32.sub
       set_local $n
       get_local $dest
       get_local $src
       i32.load
       i32.store
       get_local $dest
       i32.const 4
       i32.add
       set_local $dest
       get_local $src
       i32.const 4
       i32.add
       set_local $src
       br 0
     end
   end
)
)
