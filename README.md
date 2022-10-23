# BOMBERMAN - OOP Project

## 1. Giới thiệu

* Một phiên bản Java mô phỏng lại trò chơi [Bomberman](https://www.youtube.com/watch?v=mKIOVwqgSXM) kinh điển của NES. 
* Người chơi sẽ di chuyển, đặt bom để giết các enemy, thu thập các item để có tăng sức mạnh, tiêu diệt hết các enemy để tiến lên màn đấu mới.
* **Insert ảnh GamePlay zô đây*

## 2. Mô tả về các đối tượng

* ![pikachu](https://user-images.githubusercontent.com/100295385/197355266-b584222f-cb53-452a-9ccf-eef81183a740.png) *Pikachu* là nhân vật chính của trò chơi. Người chơi có thể điều khiển Pikachu di chuyển theo 4 hướng trái/ phải/ trên/ dưới.
* ![Bomb](https://user-images.githubusercontent.com/100295385/197355464-6dc5c83f-5294-4859-8da3-493f50d05f5f.png) *Bomb* là đối tượng mà Pikachu có thể đặt tại các ô Grass, tại điểm mà Pikachu đang đứng. Các enemy và pikachu không thể đi qua Bomb. Tuy nhiên, ngay sau vừa đặt Bomb thì Pikachu có một lần đứng ở trên Bomb để di chuyển sang các ô khác.
* ![grass 1](https://user-images.githubusercontent.com/100295385/197355675-7e8ed849-492f-4445-a08a-3dacaadd6b9a.png)
![grass 2](https://user-images.githubusercontent.com/100295385/197355678-0b6694b0-e346-47a4-a4ec-ea44c5e83df5.png) *Grass* là đối tượng và Pikachu và Bomber có thể di chuyển trên nó, và cho phép đặt Bomb tại vị trí đó.
* ![wall](https://user-images.githubusercontent.com/100295385/197355839-3d16fc33-f9c8-4d75-9a50-a899f7c3ce97.png) *Wall* là đối tượng không thể đặt Bomb lên, cũng như không thể phá hủy bằng Bomb. Pikachu và các enemy không thể di chuyển trên Wall.
* ![Brick](https://user-images.githubusercontent.com/100295385/197355940-e9adcc30-27c4-4915-93ea-40f829a5ac1b.png) *Brick* là đối tượng nằm trên các ô Grass, Pikachu không thể đặt Bomb ở đây nhưng có thể bị phá hủy với Bomb khi được kích hoạt gần nó.
* ![Portal](https://user-images.githubusercontent.com/100295385/197356014-5607d2c1-8197-482e-9877-26459853e833.png) *Portal* là đối tượng được đặt dưới ô Brick và lộ diện sau khi ô Brick đó bị phá hủy. Nếu tất cả enemy đều bị tiêu diệt thì người chơi có thể dùng Portal để đi đến màn chơi khác.
* *Item* là những đối tượng được đặt dưới ô Brick và lộ diện sau khi ô Brick đó bị phá hủy. Một số loại Item sử dụng trong trò chơi như sau:
   * ![SpeedItem](https://user-images.githubusercontent.com/100295385/197391868-5c0a8d80-b31c-4981-9a77-255b670719a4.png) *Speed Item* tăng tốc Pikachu.
   * ![FlameItem](https://user-images.githubusercontent.com/100295385/197392001-c11e0a0b-e693-44f9-af0f-a42a790f027f.png) *Flame Item* tăng độ dài của Flame.
   * ![BombItem](https://user-images.githubusercontent.com/100295385/197392139-fd5c3661-0308-448e-8202-e5c965880a61.png) *Bomb Item* được đặt Bomb liên tục.
   * ![BombPassItem](https://user-images.githubusercontent.com/100295385/197392216-e24d859f-5381-414c-ae0c-3866a7be056a.png) *BombPass Item* để Pikachu có thể đi qua Bomb.
* *Enemy* là đối tượng mà Pikachu phải tiêu diệt hết để có thể qua màn chơi mới. Tùy loại enemy mà có chức năng khác nhau. Các Enemy được sử dụng trong trò chơi như sau:
   * ![Balloom](https://user-images.githubusercontent.com/100295385/197356275-0f04c45b-2c04-4962-86b3-7b59a88c18ca.png) *Balloom* là Enemy đơn giản nhất, di chuyển ngẫu nhiên với tốc độ cố định.
   * ![Oneal](https://user-images.githubusercontent.com/100295385/197356374-69d59524-e8fc-4c27-9901-df55ea1fa632.png) *Oneal* là Enemy có thể tìm được đường đi đến Pikachu trong một bán kính nhất định (Sử dụng thuật toán BFS), Oneal sẽ tăng tốc nếu tìm được Pikachu.
   * ![Doll](https://user-images.githubusercontent.com/100295385/197376461-8b5d6e5c-713f-4113-b1fb-d1abdc4f1f96.png) *Doll* là Enemy di chuyển ngẫu nhiên và châm nhất. Sau khi chết, Doll lần lượt sẽ sinh ra 1 Balloom mới với tốc độ nhanh hơn Balloom bình thường.

   
## 3. Mô tả game play

### Điều khiển

* Game sử dụng bàn phím để điều khiển trò chơi.
* Trong menu, sử dụng các phím `↑`, `↓` để di chuyển, `Enter` để chọn.
* Người chơi điều khiển Pikachu bằng các phím `↑`, `↓`, `←`, `→` để di chuyển theo hướng muốn đi, `Space` để đặt Bomb.
* Để dừng trò chơi, nhấn phím `p`

### Cơ chế game

* Pikachu sẽ có 3 mạng để chơi trong toàn bộ trò chơi. Pikachu sẽ bị mất mạng mỗi khi va chạm với Enemy hoặc bị dính với các tia lửa phát ra khi Bomb nổ. Khi hết 3 mạng, trò chơi sẽ kết thúc.
* Khi Bomb nổ, tại vị trí đặt Bomb có một Flame trung tâm ![FlameCenter](https://user-images.githubusercontent.com/100295385/197356986-714a8727-e7db-480e-a618-4ac4c09c5675.png), và 4 Flame khác tại 4 vị trí ô đơn vị xung quang Flame trung tâm theo 4 hướng. Độ dài của 4 Flame này mặc định là 1, Pikachu có thể tăng độ dài 4 Flame này bằng cách ăn và sử dụng các Item.
* Chỉ đặt được một số lượng bomb nhất định một lúc, có thể tăng số lượng này bằng cách ăn BombItem.
* Khi Flame gặp các vật chắn như Brick/ Wall xuất hiện thì những đối tượng bên cạnh tiếp theo không bị ảnh hưởng. 
* Các Items được ăn chỉ được sử dụng trong một màn chơi và được reset lại khi sang mang chơi sau.

## 4. Tóm tắt các tính năng được cài đặt

* Bắt buộc
   * Thiết kế cây thừa kế cho các đối tượng trong game.
   * Xây dựng bản đồ màn chơi từ tệp cấu hình.
   * Di chuyển tự động Bomber(Pikachu) theo sự điều khiển của người chơi.
   * Tự động di chuyển cho các Enemy.
   * Xử lý va chạm cho các đối tượng Bomber, Enemy, Wall, Brick, Bomb.
   * Xử lý Bomb nổ.
   * Xử lý khi Bomber(Pikachu) sử dụng Item và đi vào Portal.
* Tùy chọn
   * Tìm thuật toán tìm đường cho Enemy, cài đặt thêm 1 Enemy Doll.
   * Thêm hiệu ứng âm thanh.
   
## 5. Hình ảnh demo

* Menu
* GamePlay
* Bomb nổ

## 6. Sinh viên thực hiện

| Họ và tên  | Mã sinh viên |
| ------------- |:-------------:|
| [Nguyễn Minh Tuấn](https://github.com/lataonhehe) | 21020395    |
| [Nguyễn Đức Tân](https://github.com/ductan2003)   | 21020392    |
| [Nguyễn Thị Oanh](https://github.com/NguyenOanhy) | 21020373    |










