package com.tanloc.lohu.lohuelearninguserapp.infrastructure;

import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCard;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AIFlashCardDataGenerator {
    ChatClient chatClient;

    public AIFlashCardDataGenerator(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public List<FlashCard> generateFlashCardData(String message, MultipartFile multipartFile) {
        String systemMessage = """
            Bạn là một trợ lý học tập AI. Hãy phân tích hình ảnh được cung cấp.
            Hãy tập trung trích xuất các khái niệm chính, thuật ngữ quan trọng hoặc thông tin sự kiện, kiến thức cần nhớ từ hình ảnh đó (nếu có).
            Tạo ra một danh sách các flashcard từ thông tin trích xuất được.

            Yêu cầu đầu ra:
            - Chỉ trả về một JSON Array thuần túy. Không bao bọc bởi markdown (```json ... ```).
            - Mỗi phần tử trong mảng có định dạng: {"term": "Thuật ngữ/Câu hỏi", "definition": "Định nghĩa/Câu trả lời ngắn gọn"}.
            - Nếu ảnh không có nội dung học tập, trả về mảng rỗng [].
        """;
        if (message == null && (multipartFile == null || multipartFile.isEmpty())) {
            return new ArrayList<FlashCard>();
        }

        if (message == null) {
            message = "Hãy tìm nội dung học tập trong ảnh và trả về JSON Array thuần túy. Nếu ảnh không có nội dung học tập thì trả về mảng rỗng []";
        };
        final String finalMessage = message;

        Media media = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            media = Media.builder()
                    .mimeType(MimeTypeUtils.parseMimeType(multipartFile.getContentType()))
                    .data(multipartFile.getResource())
                    .build();
        }

        final Media finalMedia = media;

        return chatClient.prompt()
                .system(systemMessage)
                .user(finalMedia != null ? promptUserSpec -> promptUserSpec.media(finalMedia).text(finalMessage)
                    : promptUserSpec -> promptUserSpec.text(finalMessage)
                )
                .call()
                .entity(new ParameterizedTypeReference<List<FlashCard>>() {
                });
    }
}
