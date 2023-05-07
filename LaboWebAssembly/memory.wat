(module
(type $_sig_i32i32i32 (func (param i32 i32 i32) ))
(type $_sig_i32i32 (func (param i32 i32) ))
(type $_sig_i32i32ri32 (func (param i32 i32) (result i32)))
;;(type $_sig_i32i32ri32ri32 (func (param i32 i32) (result i32 i32)))
(type $_sig_i32ri32 (func (param i32) (result i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_ri32 (func (result i32)))
(type $_sig_void (func ))
(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))
(import "runtime" "print" (func $print (type $_sig_i32)))
(import "runtime" "print" (func $print2 (param f32)))
(import "runtime" "read" (func $read (type $_sig_ri32)))
(memory 2000)
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(start $main)
(func $main  (type $_sig_void)
   (local $localsStart i32)
   (local $temp i32)
   i32.const 200  ;; let this be the stack size needed (params+locals+2)*4
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
   ;; instructions
   get_local $localsStart
   f32.const 1.54
   f32.store
   get_local $localsStart
   i32.const 4
   i32.add
   get_local $localsStart
   i32.load
   i32.store
   get_local $localsStart
   i32.const 4
   i32.add
   f32.load
   call $print2

   ;;get_local $localsStart
   ;;call $read
   ;;set_local $n
   ;;get_local $n
   ;;call $readn
   ;;get_local $localsStart
   ;;get_local $n
   ;;call $incn
   ;;get_local $localsStart
   ;;get_local $n
   ;;call $printn
   ;; copy params from $localsStart
   ;; call $func1
   ;; instructions

   call $freeStack
)
(func $func1 (type $_sig_ri32)
   (result i32)
   (local $temp i32)
   (local $localsStart i32)
   i32.const 200  ;; let this be the stack size needed (params+locals+2)*4
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

   ;; instructions

   ;; copy params from $localsStart
   ;; call $func1
   ;; instructions

   i32.const 0 ;; to return something
   
   call $freeStack
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
(func $freeStack (type $_sig_void) ;;funcion que libera la memoria del ultimo ambito en el que se haya entrado
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
   (result i32)
   get_global $MP
   get_global $NP
   set_global $MP
   get_global $NP
   get_local $size
   i32.add
   set_global $NP
   get_global $NP
   get_global $SP
   i32.lt_u
   if
   i32.const 3
   call $exception
   end
)
(func $freeHeap (type $_sig_void) ;;funcion que libera memoria del heap del ultimo ambito en el que nos encontrasemos
   get_global $MP
   i32.load
   i32.load offset=4
   set_global $NP
   get_global $MP
   i32.load
   set_global $MP   
)
(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest
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
(func $readn (type $_sig_i32i32)  ;;lee n numeros enteros y los guarda en las n sucesivas posiciones de dest
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
         call $read
         i32.store
         get_local $dest
         i32.const 4
         i32.add
         set_local $dest
         br 0
      end
   end
)
(func $printn (type $_sig_i32i32) ;; lee los primeros n enteros desde la posicion de memoria dest
   (param $src i32)
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
         get_local $src
         i32.load
         call $print
         get_local $src
         i32.const 4
         i32.add
         set_local $src
         br 0
      end
   end
)
(func $sumn (type $_sig_i32i32ri32) ;; dada una direccion de memoria src suma los n enteros de las sucesivas posiciones de memoria y devuelve el resultado
   (param $src i32)
   (param $n i32)
   (result i32)
   (local $res i32)
   i32.const 0
   set_local $res
   block
      loop
         get_local $n
         i32.eqz
         br_if 1
         get_local $n
         i32.const 1
         i32.sub
         set_local $n
         get_local $res
         get_local $src
         i32.load
         i32.add
         set_local $res
         get_local $src
         i32.const 4
         i32.add
         set_local $src
         br 0
      end
   end
   get_local $res
)
(func $incn (type $_sig_i32i32) ;;incrementa los n primeros enteros en las sucesivas posiciones de src en 1
   (param $src i32)
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
         get_local $src
         get_local $src
         i32.load
         i32.const 1
         i32.add
         i32.store
         get_local $src
         i32.const 4
         i32.add
         set_local $src
         br 0
      end
   end
)
(func $minmaxn (type $_sig_i32i32ri32ri32) ;; calcula el maximo y el minimo de entre los n primeros enteros desde la posicion de memoria src
   (param $src i32)
   (param $n i32)
   (result i32)
   (result i32)
   (local $max i32)
   (local $min i32)
   get_local $src
   i32.load
   set_local $min
   get_local $src
   i32.load
   set_local $max
   get_local $n
   i32.const 1
   i32.sub
   set_local $n
   get_local $src
   i32.const 4
   i32.add
   set_local $src
   block
      loop
         get_local $n
         i32.eqz
         br_if 1
         get_local $n
         i32.const 1
         i32.sub
         set_local $n
         get_local $src
         i32.load
         get_local $min
         i32.lt_u
         if
         get_local $src
         i32.load
         set_local $min
         end
         get_local $src
         i32.load
         get_local $max
         i32.gt_u
         if
         get_local $src
         i32.load
         set_local $max
         end
         get_local $src
         i32.const 4
         i32.add
         set_local $src
         br 0
      end
   end
   get_local $min
   get_local $max
)
)